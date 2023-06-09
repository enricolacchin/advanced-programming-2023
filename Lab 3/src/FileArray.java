import java.io.*;
import java.util.*;

public class FileArray {
  public final int[] array;
  public final String filePathName;
  private static final int MAX_VALUE = 1024;
  private static final int N_OF_VALUES_FOR_ROW = 5;

  // Loads an existing file
  public FileArray(String filePathName) {
    this.filePathName = filePathName;
    array = read();
  }

  // Creates new file with n random elements
  public FileArray(String filePathName, int n) {
    this.filePathName = filePathName;
    array = new int[n];
    Random random = new Random();
    for (int i = 0; i < n; i++) {
      array[i] = random.nextInt(MAX_VALUE);
    }
    write();
  }

  // Print with at most 5 elements per row
  public void print() {
    int start = 0;
    int end = N_OF_VALUES_FOR_ROW - 1;
    int maxNumber = 0;
    int maxNumberOfDigits = 0;
    for (int k : read()) {
      if (k > maxNumber) {
        maxNumber = k;
      }
    }
    if (maxNumber < 10) {
      maxNumberOfDigits = 1;
    } else if (maxNumber < 100) {
      maxNumberOfDigits = 2;
    } else if (maxNumber < 1000) {
      maxNumberOfDigits = 3;
    }

    while (start < read().length) {
      if (end > read().length) {
        end = read().length;
      }
      System.out.printf("[%02d-%02d] ", start, end);
      for (int j = start; j <= end && j < read().length; j++) {
        System.out.printf("%" + maxNumberOfDigits + "d", read()[j]);
      }
      System.out.println();
      start = end + 1;
      end = start + N_OF_VALUES_FOR_ROW - 1;
    }
  }

  // Increment all elements
  public void incrementAll() {
    for (int i = 0; i < read().length; i++) {
      read()[i]++;
    }
    write();
  }

  // Read file to array
  private int[] read() {
    try (DataInputStream input = new DataInputStream(createInputStream())) {
      int n = input.readInt();
      int[] array = new int[n];
      for (int i = 0; i < n; i++) {
        array[i] = input.readInt();
      }
      return array;
    } catch (IOException e) {
      return new int[0];
    }
  }

  // Write array to file
  private void write() {
    try (DataOutputStream output = new DataOutputStream(createOutputStream())) {
      output.writeInt(array.length);
      for (int value : array) {
        output.writeInt(value);
      }
    } catch (IOException e) {
      // ignore for now
    }
  }

  protected InputStream createInputStream() throws IOException {
    return new FileInputStream(filePathName);
  }

  protected OutputStream createOutputStream() throws IOException {
    return new FileOutputStream(filePathName);
  }
}