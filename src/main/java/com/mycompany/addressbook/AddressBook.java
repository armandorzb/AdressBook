/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.addressbook;

import java.io.*;
import java.util.*;

public class AddressBook {
    private HashMap<String, String> contacts;

    public AddressBook() {
        contacts = new HashMap<>();
    }

    // Método para cargar los contactos desde un archivo CSV
    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] contact = line.split(",");
                if (contact.length == 2) {
                    contacts.put(contact[0], contact[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Método para guardar los contactos en un archivo CSV
    public void save(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Método para listar los contactos con números de registro
    public void listWithIndices() {
        System.out.println("Contactos:");
        int index = 1;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(index + ". " + entry.getKey() + " : " + entry.getValue());
            index++;
        }
    }

    // Método para listar los contactos sin números de registro
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Método para crear un nuevo contacto
    public void create(String number, String name) {
        if (!contacts.containsKey(number)) {
            contacts.put(number, name);
            System.out.println("Contacto agregado exitosamente.");
        } else {
            System.out.println("El número ya existe en la agenda.");
        }
    }

    // Método para borrar un contacto basado en el índice del contacto
    public void delete(int index, String filename) {
        int currentIndex = 1;
        String keyToDelete = null;

        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (currentIndex == index) {
                keyToDelete = entry.getKey();
                break;
            }
            currentIndex++;
        }

        if (keyToDelete != null) {
            contacts.remove(keyToDelete);
            System.out.println("Contacto eliminado.");
            save(filename); // Guardamos los cambios en el archivo
        } else {
            System.out.println("El número de registro no es válido.");
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);
        String filename = "C:\\Users\\jarma\\Documents\\Proyecto - Actividad 4\\directorio.csv";

        addressBook.load(filename);

        while (true) {
            System.out.println("Agenda Telefónica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto por número de registro");
            System.out.println("4. Guardar y salir");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número de teléfono: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre del contacto: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    addressBook.listWithIndices(); // Mostrar la lista numerada
                    System.out.print("Ingrese el número de registro a eliminar: ");
                    int index = scanner.nextInt();
                    addressBook.delete(index, filename);
                    break;
                case 4:
                    addressBook.save(filename);
                    System.out.println("Contactos guardados. Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
