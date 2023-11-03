package util;

import contants.ShowConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowUtil {
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    public List<String> showList =new ArrayList<>();
    public static String generateOutput(String lender,String ower,double amount){
        if (amount==0 || lender.equals(ower))
            return "";
       return ower+" owes "+lender+": "+Double.parseDouble(decfor.format(amount));
    }

    public List<String> getShowList() {
        return showList;
    }

    public  void appendToShowList(String output){
        if (!showList.contains(output) && !output.equals(""))
        showList.add(output);
    }
    public void printOutput(){
        showList.stream().forEach(System.out::println);
    }

}
