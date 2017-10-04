package edu.cnm.deepdive.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Search {
  private static final String RESOURCE_BUNDLE_NAME = "usage";
  private static final String USAGE_MESSAGE_KEY = "searchMessage";
  private static final String PARSE_ERROR_MESSAGE_KEY = "parseErrorMessage";
  private static final String VALUE_ERROR_MESSAGE_KEY = "needleErrorMessage";
  private static final String READ_ERROR_MESSAGE_KEY = "haystackErrorMessage";
  

  public static void main(String[] args) {
    try {
      ResourceBundle resources = getBundle(RESOURCE_BUNDLE_NAME);
      int needle = getSearchValue(args, resources);
      Integer[] haystack = readValues(resources);
      System.out.println(findValue(needle, haystack));
    } catch (Exception ex) {
      // Do nothing
    }
  }
  private static int getSearchValue(String[] args, ResourceBundle resources)
      throws IllegalArgumentException, NumberFormatException, 
      ArrayIndexOutOfBoundsException {
    try {
      int value = Integer.parseInt(args[0]);
      if (value <= 0) {
        throw new IllegalArgumentException();
      }
      return value;
    } catch (NumberFormatException ex) {
      System.out.printf(resources.getString(PARSE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY),
          Generator.class.getName());
      throw ex;
    } catch (IllegalArgumentException ex) {
      System.out.printf(resources.getString(VALUE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY),
          Generator.class.getName());
      throw ex;
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY),
          Generator.class.getName());
      throw ex; 
    }
  }  
    private static ResourceBundle getBundle(String bundleName) {
      return ResourceBundle.getBundle(bundleName);
      
  }
    private static Integer[] readValues(ResourceBundle resources) 
        throws NumberFormatException, IOException {      
      List<Integer> data = new LinkedList<>();
      try (
          InputStreamReader reader = new InputStreamReader(System.in);
          BufferedReader buffer = new BufferedReader(reader);
      ) {
        for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
          data.add(Integer.valueOf(line));
        }
      } catch (NumberFormatException | IOException ex) {
        System.out.printf(resources.getString(READ_ERROR_MESSAGE_KEY));
        throw ex;
      }
      return data.toArray(new Integer[0]); //
    }
    
    private static int findValue(int needle, Integer[] haystack) {
      return findValue(needle, haystack, 0, haystack.length);
    }
    
    private static int findValue(int needle, Integer[] haystack, int start, int end) {
      if (end <= start) {
        return ~start;
      }
      int midpoint = (start + end) / 2; //could also write >> 1; instead of / 2;
      int test = haystack[midpoint]; //auto un-boxing
      if (test == needle) {
        return midpoint;
      }
      if (test < needle) {
        return findValue(needle, haystack, midpoint + 1, end);
      }
      return findValue(needle, haystack, start, midpoint);
    }
    
  }
