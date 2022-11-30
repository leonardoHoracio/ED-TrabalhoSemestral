package br.com.floricultura.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.floricultura.model.Endereco;

public class BuscarCep {
	
	public static Endereco buscarCep(String cep) {
		
        String json;        

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            HttpURLConnection  urlConnection = (HttpURLConnection)url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();
            System.out.println(json);
            Endereco  endereco = new Endereco();
            
            json = json.replaceAll("[{},:]", "");
            json = json.replaceAll("\"", "\n");                       
            String array[] = new String[30];
            array = json.split("\n");
            
            // JOptionPane.showMessageDialog(null, array);
            
            endereco.setLogradouro(array[7]);
            endereco.setBairro(array[15]);
            endereco.setCidade(array[19]);
            endereco.setUf(array[23]);
            
         return endereco;
            //JOptionPane.showMessageDialog(null, logradouro + " " + bairro + " " + cidade + " " + uf);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
