import java.io.*;
import java.util.*;

class HackAssembler{

    //Hashtables for label,spsymbols(includes variables) and n to assign register for variables
    public static Hashtable<String,Integer> labels=new Hashtable<String,Integer>();
    public static Hashtable<String,String> spsymbols=new Hashtable<String,String>();
    public static Integer n=16;
    public static void main(String args[]){
        String line;
        String num;
        int lc=0;
        //First Pass to assign labels with pc(here lc)
        try{
            BufferedReader fbr=new BufferedReader(new FileReader(args[0]));
            while((line=fbr.readLine())!=null){
                line=line.trim();
                if(!line.matches("\n*") && !line.matches("//.*")){
                    if(line.indexOf('(')==0){
                        line = line.replace(")","");
                        Storeval(line.substring(1),lc);
                        continue;
                    }
                    else if(line.indexOf('@')==0|| line.indexOf(';')<=7|| line.indexOf('=')<=7){
                        lc++;
                    }
                }
            }
            fbr.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe);
        }
        catch(IOException e){
            System.out.println(e);
        }
        //Second Pass
        try{
        BufferedReader br=new BufferedReader(new FileReader(args[0]));
        String name=args[0].substring(0,args[0].indexOf('.'));
        BufferedWriter bw=new BufferedWriter(new FileWriter(name+".hack"));
            while((line=br.readLine())!=null){
                if(!line.matches("\n*") && !line.matches("//.*")){
                    line=line.trim();
                    if(line.charAt(0)=='@'){  // A-Instruction
                        num=checkifPresent(line.substring(1));
                        line=convertBinary(num);
                        //System.out.println(line);
                        bw.write(line+"\n");
                        continue;
                    }
                    else if(line.contains("=") || line.contains(";")){
                        String cins="";
                        if(line.contains(";")){
                            String[] jmp=line.split(";");
                            jmp[1]=coreVal(jmp[1]);
                            //for jump instruction dest bits are zero
                            cins+="111"+comp(jmp[0])+"000"+jump(jmp[1]);
                        }
                        else{
                        String[] eqn=line.split("=");
                        eqn[1]=coreVal(eqn[1]);
                        //NO jump instruction
                        cins+="111"+comp(eqn[1])+dest(eqn[0])+"000";
                        }
                        line=cins;
                        //System.out.println(line);
                        bw.write(line+"\n");
                        
                    }
                }
            }
            bw.close();
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe);
        }
        catch(IOException e){
            System.out.println(e);
        }
            }
    //Binary Conversion
    static String convertBinary(String s){
        int ds=Integer.parseInt(s);
        String zeros="";
        String  num=Integer.toBinaryString(ds);
        for(int i=0;i<15-num.length();i++){
            zeros+='0';
        }
        return "0"+zeros+num;

    }
    //jump bits
    static String jump(String jmp){
        Hashtable<String,String> jumpbits=new Hashtable<String,String>();
        jumpbits.put("","000");
        jumpbits.put("JGT","001");
        jumpbits.put("JEQ","010");
        jumpbits.put("JGE","011");
        jumpbits.put("JLT","100");
        jumpbits.put("JNE","101");
        jumpbits.put("JLE","110");
        jumpbits.put("JMP","111");
        return jumpbits.get(jmp);
    }
    //dest bits
    static String dest(String dst){
        Hashtable<String,String> destbits=new Hashtable<String,String>();
        destbits.put("","000");
        destbits.put("M","001");
        destbits.put("D","010");
        destbits.put("MD","011");
        destbits.put("A","100");
        destbits.put("AM","101");
        destbits.put("AD","110");
        destbits.put("AMD","111");
        return destbits.get(dst);
    }
    //comp bits
    static String comp(String cmp){
        Hashtable<String,String> compbits=new Hashtable<String,String>();
        compbits.put("0","0101010");
        compbits.put("1","0111111");
        compbits.put("-1","0111010");
        compbits.put("D","0001100");
        compbits.put("A","0110000");
        compbits.put("M","1110000");
        compbits.put("!D","0001101");
        compbits.put("!A","0110001");
        compbits.put("!M","1110001");
        compbits.put("-D","0001111");
        compbits.put("-A","0110011");
        compbits.put("-M","1110011");
        compbits.put("D+1","0011111");
        compbits.put("A+1","0110111");
        compbits.put("M+1","1110111");
        compbits.put("D-1","0001110");
        compbits.put("A-1","0110010");
        compbits.put("M-1","1110010");
        compbits.put("D+A","0000010");
        compbits.put("D+M","1000010");
        compbits.put("D-A","0010011");
        compbits.put("D-M","1010011");
        compbits.put("A-D","0000111");
        compbits.put("M-D","1000111");
        compbits.put("D&A","0000000");
        compbits.put("D&M","1000000");
        compbits.put("D|A","0010101");
        compbits.put("D|M","1010101");
        return compbits.get(cmp);
    }
    //store labels
    static void Storeval(String label,int lc){
        labels.put(label,lc);
    }
    //check presence in tables and work accordingly
    static String checkifPresent(String ains){
            ains=coreVal(ains);
            spsymbols.put("R0","0");
            spsymbols.put("R1","1");
            spsymbols.put("R2","2");
            spsymbols.put("R3","3 ");
            spsymbols.put("R4","4");
            spsymbols.put("R5","5");
            spsymbols.put("R6","6");
            spsymbols.put("R7","7");
            spsymbols.put("R8","8");
            spsymbols.put("R9","9");
            spsymbols.put("R10","10");
            spsymbols.put("R11","11");
            spsymbols.put("R12","12");
            spsymbols.put("R13","13");
            spsymbols.put("R14","14");
            spsymbols.put("R15","15");
            spsymbols.put("SCREEN","16384");
            spsymbols.put("KBD","24576");
            spsymbols.put("SP","0");
            spsymbols.put("LCL","1");
            spsymbols.put("ARG","2");
            spsymbols.put("THIS","3");
            spsymbols.put("THAT","4");
            if(spsymbols.containsKey(ains)){
                return spsymbols.get(ains);
            }
            else if(labels.containsKey(ains)){
                return Integer.toString(labels.get(ains));
            }
            else if(ains.matches("[0-9].*")){
                return ains;

            }
            else{
                spsymbols.put(ains,Integer.toString(n));
                n++;
                return spsymbols.get(ains);
            }
            }



    //trimming and removing extra content from lines
    static String coreVal(String str){

        if(str.indexOf('/')!=-1){
            str=str.substring(0, str.indexOf('/'));
        }
        str=str.trim();
        return str;
        }
        


    

}
