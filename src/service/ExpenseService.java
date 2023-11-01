package service;

import dto.Expense;
import dto.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
     User settleBalance(String user_id, List<Expense> expenseList){
        User user;
        if (!UserRepository.getStringUserMap().containsKey(user_id))
         user=new User(user_id,new HashMap<>(),new HashMap<>());
        else
         user= UserRepository.getStringUserMap().get(user_id);

       Map<String,Expense> lendToMap=user.getLendToMap();
        for (Expense expense:expenseList) {
                if (!lendToMap.containsKey(expense.getUser_id()) && !user.getOwesToMap().containsKey(expense.getUser_id()))
                {
                    lendToMap.put(expense.getUser_id(), expense);
                    updateOwesTo(user_id,expense);
                }else
                if (lendToMap.containsKey(expense.getUser_id())){
                    Expense expense1=new Expense(expense.getUser_id(),lendToMap.get(expense.getUser_id()).getAmount()+expense.getAmount());
                    lendToMap.put(expense.getUser_id(),expense1);
                    updateOwesTo(user_id,expense1);
                    expense=expense1;
                }
                if (user.getOwesToMap().containsKey(expense.getUser_id())){
                  getsettleExpenses(user_id,user.getOwesToMap().get(expense.getUser_id()),expense);
                }
        }
        return user;
    }
    void getsettleExpenses(String user_id,Expense owes,Expense expense){
        double diffAmount=expense.getAmount()-owes.getAmount();
        if (diffAmount>0){
            expense.setAmount(diffAmount);//u4 expense on u1=230 rem
            UserRepository.getStringUserMap().get(user_id).getOwesToMap().remove(owes.getUser_id());
            UserRepository.getStringUserMap().get(expense.getUser_id()).getLendToMap().remove(user_id);
            UserRepository.getStringUserMap().get(user_id).getLendToMap().put(expense.getUser_id(),expense);
            updateOwesTo(user_id,expense);//for other
        }else {
            owes.setAmount(Math.abs(diffAmount));
            UserRepository.getStringUserMap().get(expense.getUser_id()).getLendToMap().put(user_id,owes);
            UserRepository.getStringUserMap().get(user_id).getLendToMap().remove(expense.getUser_id());
            UserRepository.getStringUserMap().get(user_id).getOwesToMap().remove(owes.getUser_id(),owes);

        }
    }

    List<Expense> getExpenseList(String lender,List<String> borrowers,List<Double> spendAmount){
        List<Expense> expenseList=new ArrayList<>();
        int i=0;
        for (String borrower:borrowers) {
            if (lender.equals(borrower))
                continue;
            expenseList.add(new Expense(borrower,spendAmount.get(i++)));
        }
        return expenseList;
    }

     void updateOwesTo(String lender_id, Expense expense){
        if (expense.getUser_id().equals(lender_id))
            return;
        if (!UserRepository.getStringUserMap().containsKey(expense.getUser_id())){
            Expense owedExpense=new Expense(lender_id, expense.getAmount());
            Map<String,Expense> owedExpenseMap=new HashMap<>();
            owedExpenseMap.put(lender_id,owedExpense);
            User user=new User(expense.getUser_id(),new HashMap<>(),owedExpenseMap);
            UserRepository.getStringUserMap().put(expense.getUser_id(),user);
            UserRepository.getUserList().add(user);
        }else {
            User user=UserRepository.getStringUserMap().get(expense.getUser_id());
            Expense owedExpense=new Expense(lender_id, expense.getAmount());
            user.getOwesToMap().put(lender_id,owedExpense);
            UserRepository.getStringUserMap().put(lender_id,user);
        }
    }
}
