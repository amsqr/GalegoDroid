package es.amsqr.galegodroid;
import android.text.Html;
import android.text.Spanned;
import java.net.*;
import java.io.*;

/*
 * Copyright (C) 2009 Enrique Benimeli Bofarull <ebenimeli.dev@gmail.com>
 * Copyright (c) 2012 Alejandro Mosquera <amsqr@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

public class ApertiumWS {

    private String sl;
    private String tl;
    private String text;
    private String translation;
    private boolean markUnknown;

    public ApertiumWS(String sl, String tl, String text) {
        this.sl = sl;
        this.tl = tl;
        this.text = text;
        
    }



    public final String translate() {
        String nextLine;
        URL url = null;
        URLConnection urlConn = null;
        InputStreamReader inStream = null;
        BufferedReader buff = null;
        try {
            text = URLEncoder.encode(text,"UTF-8");
            
            String theURL = "http://api.apertium.org/json/translate?q=" + text + "&langpair=" + sl + "|" + tl;
            url = new URL(theURL);
            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff = new BufferedReader(inStream);

            // Read and print the lines from index.html
            String trans = "";
            while (true) {
                nextLine = buff.readLine();
                if (nextLine != null) {
                    //System.out.println(nextLine);
                    trans += nextLine;
                } else {
                    break;
                }
            }
            int in = trans.indexOf("translatedText");
			int fin =trans.indexOf("\\n");
			if ((in>0) && (fin >0))
			{
				trans = trans.substring(in+17,fin);
			}
			else 
				{
				trans = "error" ;
				}
				
			
            this.setTranslation(trans);
            translation = ((Spanned)Html.fromHtml(translation)).toString();
            return this.translation;
        } catch (MalformedURLException e) {
            System.out.println("Please check the URL:" +
                    e.toString());
            return e.toString();
        } catch (IOException e1) {
            System.out.println("Can't read  from the Internet: " +
                    e1.toString());
            return e1.toString() + "/ " +  e1.getMessage();
        }
        //return "No funciona.";
    }

    /**
     * @return the translation
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * @param translation the translation to set
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }
}

