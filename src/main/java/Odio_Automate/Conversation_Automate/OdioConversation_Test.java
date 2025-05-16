package Odio_Automate.Conversation_Automate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OdioConversation_Test
{
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	
	
	public void browserLaunch() {
		driver.get("http://65.108.148.94/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void login() {
		driver.findElement(By.id("inputEmail")).sendKeys("demousa@gmail.com");
		driver.findElement(By.id("inputChoosePassword")).sendKeys("password");
		driver.findElement(By.xpath("//*[@type='submit']")).click();
		
		WebElement card = driver.findElement(By.xpath("//*[@class='mb-1 text-dark font-14 carusal-data']"));
		wait.until(ExpectedConditions.visibilityOf(card));
	}
	
	public void browserClose() {
		driver.quit();
	}
	public void dashboardFilter() {
		WebElement filterSwitch = driver.findElement(By.xpath("//*[@class='bx bx-filter-alt']"));
		wait.until(ExpectedConditions.elementToBeClickable(filterSwitch)).click();
		
//		WebElement momentBktDrop = driver.findElement(By.xpath("(//*[@name='role'])[1]"));
//		wait.until(ExpectedConditions.visibilityOf(momentBktDrop));
//		Select selMBkt = new Select(momentBktDrop);
//		selMBkt.selectByVisibleText("NexusBucket");
		
		WebElement coeDrop = driver.findElement(By.xpath("//*[@name='role' and @placeholder ='Select COE']"));
		wait.until(ExpectedConditions.visibilityOf(coeDrop));
		Select selCOE = new Select(coeDrop);
		selCOE.selectByVisibleText("New York");
		
		WebElement selectDateDrop = driver.findElement(By.xpath("//*[@name='selectDate']"));
		wait.until(ExpectedConditions.visibilityOf(selectDateDrop));
		Select selDate = new Select(selectDateDrop);
		selDate.selectByVisibleText(" Last Year ");
		
		WebElement applyBtn = driver.findElement(By.xpath("//*[@type='submit']"));
		wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();
		
		WebElement card = driver.findElement(By.xpath("//*[@class='mb-1 text-dark font-14 carusal-data']"));
		wait.until(ExpectedConditions.visibilityOf(card));
		
	}
	
	public void conversationsPage() throws InterruptedException  {
		WebElement logoIcon = driver.findElement(By.xpath("//*[@class='logo-icon']"));
		logoIcon.click();
		
		WebElement conversationsDrop = driver.findElement(By.xpath("//*[text()='Conversations']"));
		conversationsDrop.click();
		WebElement voice = driver.findElement(By.xpath("//*[text()='Voice']"));
		voice.click();
		
		WebElement cardContainer = driver.findElement(By.xpath("//*[@class='card-container']"));
		wait.until(ExpectedConditions.visibilityOf(cardContainer));
		
		WebElement groupView = driver.findElement(By.xpath("//*[@value='Group']"));
		groupView.click();
		Thread.sleep(1000);
		
	}
	
	public void conversationPagination() throws InterruptedException {
		
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    
	    WebElement pagination = driver.findElement(By.xpath("//*[@class='pagination']"));
	    
	    wait.until(ExpectedConditions.elementToBeClickable(pagination));
	   // js.executeScript("arguments[0].scrollIntoView();", pagination);
	    js.executeScript("window.scrollBy(0, 1000);");
	    Thread.sleep(1000);

	    List<Integer> callsCount = new ArrayList<>();

	    while (true) {
	        List<WebElement> callCards = driver.findElements(By.xpath("//*[@class='salesCallCard card-body']"));
	        
	        if (!callCards.isEmpty()) {
	            callsCount.add(callCards.size());
	        }

	        try {
	            WebElement nextPageBtn = driver.findElement(By.xpath("//*[@aria-label='Next page']"));

	            if (!nextPageBtn.isEnabled()) {
	                break;
	            }

	            js.executeScript("arguments[0].scrollIntoView();", nextPageBtn);
	            nextPageBtn.click();

	            wait.until(ExpectedConditions.stalenessOf(callCards.get(0)));
	        } 
	        catch(NoSuchElementException e) {
	            System.out.println("No Next Page button found, stopping pagination.");
	            break;
	        }
	    }

	    System.out.println("Total call counts per page: " + callsCount);
	}

	public void groupcount()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		List<WebElement> calls = driver.findElements(By.xpath("//*[@class=\"salesCallCard card-body\"]"));
		System.out.println("number of callgroup: "+calls.size());
	}
	




	public static void main(String[] args) throws InterruptedException {
		OdioConversation_Test obj = new OdioConversation_Test();
		obj.browserLaunch();
		obj.login();
		obj.dashboardFilter();
		obj.conversationsPage();
		obj.groupcount();
//		obj.conversationPagination();
		
		//obj.browserClose();
	}
	

}

