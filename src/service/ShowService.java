package service;

import contants.Errors;
import dto.User;
import repository.UserRepository;
import util.ShowUtil;

import java.util.List;

public class ShowService {
    ShowUtil showUtil;
    public ShowService(ShowUtil showUtil) {
        this.showUtil =showUtil;
    }

    void showAllBalance(){

        List<User> userList= UserRepository.getUserList();
        if (userList.isEmpty()) {
            System.out.println(Errors.NO_BALANCE);
            return;
        }
        int BalanceFlag =0;
        for (User user:userList) {
            for (String lendTo: user.getLendToMap().keySet()) {
                BalanceFlag =1;
                double lendAmount=user.getLendToMap().get(lendTo).getAmount();
                if (lendAmount>0)
                    showUtil.appendToShowList(ShowUtil.generateOutput(user.getName(),lendTo,lendAmount));
                else
                if (lendAmount<0)
                    showUtil.appendToShowList(ShowUtil.generateOutput(lendTo,user.getName(),-lendAmount));
            }
        }
        showUtil.printOutput();
        if (BalanceFlag ==0)
            System.out.println(Errors.NO_BALANCE);
    }
      void showUserBalance(String user_id){
        if (!UserRepository.getStringUserMap().containsKey(user_id)){
            System.out.println(Errors.NO_BALANCE);
            return;
        }
        User user=UserRepository.getStringUserMap().get(user_id);
        int BalanceFlag =0;

        for (String lendTo: user.getLendToMap().keySet()) {
            BalanceFlag =1;
            double lendAmount=user.getLendToMap().get(lendTo).getAmount();
            if (lendAmount>0)
                showUtil.appendToShowList(ShowUtil.generateOutput(user.getName(),lendTo,lendAmount));
            else
            if (lendAmount<0)
                showUtil.appendToShowList(ShowUtil.generateOutput(lendTo,user.getName(),-lendAmount));
        }
        showUtil.printOutput();
        if (BalanceFlag ==0)
            System.out.println(Errors.NO_BALANCE);
    }
}
