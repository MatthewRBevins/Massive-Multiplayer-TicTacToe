// import statements for establishing   
// connection with the API and reading data from it.  
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.io.BufferedReader;
public class HttpURLConnectionExample
{
    public void GETRequest() throws IOException
    {
        String urlName = "http://127.0.0.1:8080";
        URL urlForGetReq = new URL(urlName);
        String read = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetReq.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("userId", "abcdf"); // set userId its a sample here
        int codeResponse = connection.getResponseCode();
// checking whether the connection has been established or not  
        if (codeResponse == HttpURLConnection.HTTP_OK)
        {
// reading the response from the server  
            InputStreamReader isrObj = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(isrObj);
// to store the response from the servers  
            StringBuffer responseStr = new StringBuffer();
            while ((read = bf .readLine()) != null)
            {
                responseStr.append(read);
            }
// closing the BufferedReader  
            bf.close();
// disconnecting the connection  
            connection.disconnect();
// print the response  
            System.out.println("JSON String Result is: \n" + responseStr.toString());
        }
        else
        {
            System.out.println("GET Request did not work");
        }
    }
    // main method
    public static void main(String argvs[]) throws IOException
    {
        // creating an object of the JavaGETExample class
        HttpURLConnectionExample getObj = new HttpURLConnectionExample();

        // invoking the method GETRequest()
        getObj.GETRequest();
    }
}  