package panasyuk.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import panasyuk.main.FileDto;

public class FileComparator implements Comparator<FileDto> {

    @Override
    public int compare(FileDto f1, FileDto f2) {
        if (f1.isDirectory() && !f2.isDirectory()) {
            return -1;
        } else if (f2.isDirectory() && !f1.isDirectory()) {
            return 1;
        }
        List<String> elements1 = convertToComparableElements(f1.getName());
        List<String> elements2 = convertToComparableElements(f2.getName());

        for (int i = 0; i < Math.min(elements1.size(), elements2.size()); i++) {
            String el1 = elements1.get(i);
            String el2 = elements2.get(i);
            int result;
            if (el1.equals(".") && (!el2.equals("."))) {
                result = -1;
            } else if (el2.equals(".") && (!el1.equals("."))) {
                result = 1;
            } else if (isNumber(el1) && !isNumber(el2)) {
                result = -1;
            } else if (isNumber(el2) && !isNumber(el1)) {
                result = 1;
            } else if (isNumber(el1) && isNumber(el2)) {
                result = Integer.compare(Integer.parseInt(el1), Integer.parseInt(el2));
            } else {
                result = el1.compareTo(el2);
            }
            if (result != 0) {
                return result;
            }
        }
        return 1;
    }

    /**
     * If there is a sequence it converts it into one element
     */
    private List<String> convertToComparableElements(String name) {
        char[] charArray = name.toLowerCase().toCharArray();
        List<String> resultList = new ArrayList<>();
        String tempForDigits = "";
        for (int i = 0; i < charArray.length; i++) {
            String el = String.valueOf(charArray[i]);
            if (!isNumber(el)) {
                if (!tempForDigits.equals("")) {
                    resultList.add(tempForDigits);
                    tempForDigits = "";
                }
                resultList.add(el);
                continue;
            } else {
                tempForDigits += el;
            }
        }
        return resultList;
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
