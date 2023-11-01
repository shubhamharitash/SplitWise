package util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputUtil {
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    public List<String> outputList=new ArrayList<>();
    public static String generateOutput(String lender,String ower,double amount){
        if (amount==0 || lender.equals(ower))
            return "";
       return ower+" owes "+lender+": "+Double.parseDouble(decfor.format(amount));
    }
    public  void appendToOutputList(String output){
        if (!outputList.contains(output) && !output.equals(""))
        outputList.add(output);
    }
    public void printOutput(){
        outputList.stream().forEach(System.out::println);
    }
}
