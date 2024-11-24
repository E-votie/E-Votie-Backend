package com.evotie.email_ms;

import com.evotie.email_ms.Model.Massage;

import java.net.*;
import java.io.*;

public class URLReader {
    public static void sendMassage(Massage message) throws Exception {

        URL textit = new URL("https://textit.biz/sendmsg/index.php?id=94718696971&pw=8915&to=94"+message.getTo().replace("-", "947")+"&text=" + URLEncoder.encode(message.getBody(), "UTF-8"));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(textit.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
