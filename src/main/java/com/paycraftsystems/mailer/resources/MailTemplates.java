/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.mailer.resources;

import com.paycraftsystems.mailer.dto.MailObj;


/**
 *
 * @author root
 */
public class MailTemplates {
    
    
    
    
    
    public String doMail(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>CU PIN Notification :::</title>"+
		"<style>"+
		".tdBg { background-color: #050941 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong> Covenant University Fees Payment Notification </strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>Find below your profile details. If you have problems with using this information, kindly contact us via</p>"+
		"      <p>payments@covenantmfb.com.ng or call 01-454-5571"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Payment Reference :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'></td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Payment Date :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getEventDate()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Payment Amount :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getAmount()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Receipt No:</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getReceiptNo()+"</td>"+
		"  </tr>"+
		"  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>PIN :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getPin()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>STATUS :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getOtherInfo()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>Covenant Micro Finance Bank</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"www.pethahiah.com/\"><b>PayThru</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>PayThru Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
        
        }
        
     return resp;
    }
    
    
    
    public String doWelcomeMail(MailObj obj) {
        String resp = "";
        System.out.println(" called doWelcomeMail = " + obj);
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>Welcome Mail :::</title>"+
		"<style>"+
		".tdBg { background-color: #050941 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong> We have been waiting for you, since forever </strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>Find below your profile details. If you have problems with using this information, kindly contact us via</p>"+
		"      <p>support@78financialsolutions.com or call 01-ASK-78FS"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Default Password :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getDefaultPassword()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Sign On Date :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getEventDate()+"</td>"+
		"  </tr>"+
                 "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Role:</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getRoleName()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>78 Financial Solutions</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"www.pethahiah.com/\"><b>PayThru</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>PayThru Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
            
            e.printStackTrace();
        
        }
        
     return resp;
    }
    
    
    public String doResetMail(MailObj obj) {
        String resp = "";
        System.out.println(" called doResetMail = " + obj);
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>Reset Password Notification :::</title>"+
		"<style>"+
		".tdBg { background-color: #050941 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>Password Reset</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>You are receiving this mail, because you initiated a reset password process, if you encounter any challenge kindly contact us via</p>"+
		"      <p>support@paythru.ng or call 01-ASK-PATHRU"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Reset Password Link :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.getOtherInfo()+"/\"><b>RESET</b></a>"+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Reset Date :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getEventDate()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>PayThru</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"www.pethahiah.com/\"><b>PayThru</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>PayThru Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
            
            e.printStackTrace();
        
        }
        
     return resp;
    }
    
    public String doDefaultPasswordMail(MailObj obj) {
        String resp = "";
        System.out.println(" called doResetMail = " + obj);
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>Default Password Notification :::</title>"+
		"<style>"+
		".tdBg { background-color: #050941 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>Password Reset</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>Kindly find your default password below, if you encounter any challenge kindly contact us via</p>"+
		"      <p>support@paythru.ng or call 01-ASK-PATHRU"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Default Password :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'><b>"+obj.getOtherInfo()+"</b></td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Event Date :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getEventDate()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>PayThru</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"www.pethahiah.com/\"><b>PayThru</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>PayThru Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
            
            e.printStackTrace();
        
        }
        
     return resp;
    }
    
    
    public String doTokenExpiryMail(MailObj obj) {
        String resp = "";
        System.out.println(" called doResetMail = " + obj);
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>Token Expiry Notification :::</title>"+
		"<style>"+
		".tdBg { background-color: #050941 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>Password Reset</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>Kindly note that your all tokens generated with your present set of credentials will expire shortly, if you encounter any challenge kindly contact us via</p>"+
		"      <p>support@paycraftsystems.co or call 01-ASK-PAYCRAFT"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Your Token expires in :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'><b>"+obj.getOtherInfo()+"hrs</b></td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Event Date :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getEventDate()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>Paycraft</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"www.paycraftsystems.co/\"><b>Paycraft Authentication Service</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>Paycraft Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
            
            e.printStackTrace();
        
        }
        
     return resp;
    }
    
    
		
    
}
