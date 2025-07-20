package pages;

import org.openqa.selenium.By;

public class AccountDeleted
{
    By delete_success_text = By.xpath("//*[@id=\"form\"]/div/div/div/h2/b");
    By delete_success_paragraph = By.xpath("//*[@id=\"form\"]/div/div/div/p[2]");
    By delete_success_message = By.xpath("//*[@id=\"form\"]/div/div/div/p[1]");
    By continue_button= By.xpath("//*[@id=\"form\"]/div/div/div/div/a");

    public void verifyAccountDeleted(){
       // user should be shown delete_success_message,delete_success_paragraph,delete_success_text
    }

    public void continueDeletion(){
        //after clicking continue_button, user should be navigated to home page
}
}
