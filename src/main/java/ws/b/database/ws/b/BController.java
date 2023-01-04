/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.database.ws.b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.b.database.ws.b.exceptions.NonexistentEntityException;

/**
 *
 * @author lenovo
 */
@Controller
@ResponseBody
public class BController {
    Untitled data = new Untitled();
    UntitledJpaController bctrl = new UntitledJpaController();
    
  List<Untitled> lsdata=new ArrayList();
    
    
    @RequestMapping("/getName/{id}")
    public String getName(@PathVariable("id") int id){
        try{
            data = bctrl.findUntitled(id);
        return "Nama: "+ data.getNama()+ "\nTanggal Lahir: "+data.getTglLahir().toString();
        }
        catch(Exception error){
            return "Data tidak ada";
        }
    }
    @RequestMapping("/getAll")
    public List<Untitled> viewAll(){
        return bctrl.findUntitledEntities();
    }
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") int id){
        try{
             bctrl.destroy(id);
              return id +""+ "deleted";
        }
        catch(NonexistentEntityException error){
            return "error";
        }
    }
    @RequestMapping("/create")
    public String createNewData(){
        String message="No Action";
        return message;
    }
//    @RequestMapping(value="/getName/{id}",method=RequestMethod.PUT)
//    public String editById(@PathVariable("id") int id){
//        try{
//            bctrl.
//            return id +""+"Berhasil diupdate";
//        }
//        catch(NonexistentEntityException error){
//            return "error";
//        }
//    }
}
