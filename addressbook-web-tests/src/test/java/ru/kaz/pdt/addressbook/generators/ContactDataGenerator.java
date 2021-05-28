package ru.kaz.pdt.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.solidfire.gson.Gson;
import com.solidfire.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.kaz.pdt.addressbook.model.ContactData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;
  private Properties properties;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private List<ContactData> generateContacts(int count) throws IOException {
    System.getProperty("target", "local");
    String target = System.getProperty("target", "local");
    properties = new Properties();
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties",target))));
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format(properties.getProperty("firstName"), i))
              .withLastname(String.format(properties.getProperty("lastName"), i)).withAddress(String.format(properties.getProperty("address"), i))
              .withMobilePhone(String.format(properties.getProperty("mobilePhone"), i)).withEmail(String.format(properties.getProperty("email"), i))
              .withPhoto(new File(String.format(properties.getProperty("photo"), i))));
    }
    return contacts;
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format" + format);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(),
                contact.getLastname(), contact.getAddress(), contact.getMobilePhone(),
                contact.getEmail(), contact.getPhoto().getPath()));
      }
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

}
