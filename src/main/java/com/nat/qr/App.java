package com.nat.qr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.konylabs.middleware.dataobject.Param;
import com.nat.qr.QRUtility;

/**
 * Hello world!
 *
 */
public class App 
{
	

    public static void main(String[] args) {
    	try {
            
            // Write
            String input_acc_id = "0910107494";
            String pp_acc_id = "";
            String pp_amount = "";
            String pp_chksum = "";
			//jenkins test
            // process input_acc_id
            if(input_acc_id.length() == 15){ // truemoney e-wallet
                pp_acc_id = "0315" + input_acc_id;
            }
            else if(input_acc_id.length() == 13){ // card-id
                pp_acc_id = "0213" + input_acc_id;
            }
            else if(input_acc_id.length() == 10){ // tel-no
                pp_acc_id = "01130066" + input_acc_id.substring(1);
            }

            // process amount
            String amount = "2000";
            if(!amount.isEmpty()){
                pp_amount = String.format("54%02d%s", amount.length(), amount);
            }

            // build pp string
            String field_29 = "0016A000000677010111" + pp_acc_id;
            String pp_str = "000201010211"
                          + "29" + field_29.length() + field_29                        
                          + "5802TH"
                          + pp_amount
                          + "5303764"
                          + "6304";

            // process checksum
            pp_chksum = QRUtility.crc16(pp_str);
            System.out.println("input for check sum: " + pp_chksum);
            
            pp_str += pp_chksum;
            //System.out.println("PP String : " + pp_str);
            //String qrCodeData = decodedText; 
            String qrCodeData = pp_str;
    		String filePath = "D:\\Docs\\generated.jpg";
    		String charset = "UTF-8";
    		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
    		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    		String imageString = QRUtility.createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
    		System.out.println("imageString = " + imageString);
    		System.out.println("QR Code image created successfully!");
    		//System.out.println("QR Code image created successfully!");
    		// CRC-16
    		//String sampleTag = decodedText.substring(0, decodedText.length() - 4);
    		//System.out.println("input for check sum: " + sampleTag);
    		//System.out.println("input for check sum: " + sampleTag);
    		//String crc16 = QRUtility.crc16(sampleTag.getBytes());
    		//LOG.info("crc16 = " + crc16);
    		
        	// Read
            File file = new File("D:\\Docs\\generated.jpg");
            String decodedText = QRUtility.decodeQr(file);
            System.out.println("Decoded text = " + decodedText);
            
            String s1 = "0011000109200200";
            s1 = s1.replace("920", "921");
            System.out.println("replaced no :" + s1); 
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
            //LOG.info("Could not decode QR Code, IOException :: " + e.getMessage());
        } catch (NotFoundException e) {
        	System.out.println("No QR Code found in the image");
        	//LOG.info("No QR Code found in the image");
		} catch (WriterException e) {
			e.printStackTrace();
		}
    }
    
}
