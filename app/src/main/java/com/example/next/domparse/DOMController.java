package com.example.next.domparse;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by next on 8/3/17.
 */
public class DOMController {
    IDocumentData iDocumentData;

    public DOMController(IDocumentData iDocumentData) {
        this.iDocumentData = iDocumentData;
    }

    public void callDOMTask(String URL) {
        new DownloadXMLTask(iDocumentData).execute(URL);
    }
    private class  DownloadXMLTask extends AsyncTask<String, Void, NodeList> {
        IDocumentData iDocumentData;
        Context mContext;
        private static final String TAG = "DownloadXMLTask";
        public DownloadXMLTask(IDocumentData data) {
            this.iDocumentData = data;
            // this.mContext = applicationContext;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: ");
        }



        @Override
        protected NodeList doInBackground(String... params) {
            Log.i(TAG, "doInBackground: ");
            NodeList nodeList = null;
            try{
                // Toast.makeText(mContext, "parsing", Toast.LENGTH_SHORT).show();
                URL url = new URL(params[0]);
                Log.e(TAG, "doInBackground: "+params[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = dbf.newDocumentBuilder();
                //down load the xml file
                Document document = builder.parse(new InputSource(url.openStream()));
                document.getDocumentElement().normalize();
                nodeList = document.getElementsByTagName("item");


            } catch (Exception e) {
                Log.e(TAG, "doInBackground:error "+e.getMessage());

            }

            return nodeList;
        }

        @Override
        protected void onPostExecute(NodeList nodeList) {
            super.onPostExecute(nodeList);
            Log.i(TAG, "onPostExecute: "+nodeList.getLength());
            if (nodeList != null && nodeList.getLength() > 0) {
                iDocumentData.getData(nodeList);
            }


        }


    }



}

