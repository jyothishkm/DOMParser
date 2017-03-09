package com.example.next.domparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IDocumentData{
Button button;
    TextView mDisplayData;
    String URL = "http://www.androidbegin.com/tutorial/XMLParseTutorial.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    button = (Button) findViewById(R.id.button);
        mDisplayData = (TextView)findViewById(R.id.title_txt);


        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       DOMController domController = new DOMController(this);
        domController.callDOMTask(URL);

    }

    @Override
    public void getData(NodeList nodeList) {
        Log.i("MainActivity", "getData: "+nodeList.getLength());
        for (int i = 0; i <nodeList.getLength() ; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
// Set the texts into TextViews from item nodes
                // Get the mDisplayData
                mDisplayData.setText(mDisplayData.getText() + "Title : "+ getNode("title", element)  + "\n"+"\n");
            //get the desc
                mDisplayData.setText(mDisplayData.getText() + "Desc:" +getNode("description", element)+ "\n"+"\n");
                //get the link
                mDisplayData.setText(mDisplayData.getText() + "Link:" +getNode("link", element)+ "\n"+"\n");
                //get the date
                mDisplayData.setText(mDisplayData.getText() + "Date:" +getNode("date", element)+"\n" + "\n"+"\n"+"\n");

            }
        }

    }
    private String getNode(String sTag, Element element) {
        NodeList nodeList = element.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nodeValue = nodeList.item(0);
        Log.i("data", "getNode: "+nodeValue.getNodeValue());
        return nodeValue.getNodeValue();
    }
}
