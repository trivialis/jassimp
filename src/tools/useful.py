import pyperclip
import re

CLASSMETHOD=re.compile("\s*([A-Za-z<>]+::)")
OPERATORMETHOD=re.compile("(operator\s*([()=!<>*/+\-]{1,2}))\s*")

def op_name(op):
    if "*" in op:
        return "opMultiply"
    if "/" in op:
        return "opDivide"
    if "+" in op:
        return "opAdd"
    if "-" in op:
        return "opSubtract"
    if "==" in op:
        return "opEquals"
    if "()" in op:
        return "opCast"
    if "!=" in op:
        return "opNotEquals"
    return ""

def clean_class_method_declaration(declarationline=""):
    result = "public "
    if declarationline.startswith("inline "):
        declarationline=declarationline.replace("inline ","")
    if "operator" in declarationline:
        r=re.findall(OPERATORMETHOD, declarationline)
        if r:
            replac=r[0][0]
            op=op_name(r[0][1])
            if op:
                declarationline = declarationline.replace(replac, op)
            
    if "::" in declarationline:
       
        r=re.findall(CLASSMETHOD, declarationline)
        if r:
            declarationline=declarationline.replace(r[0], "")
    declarationline=declarationline.replace("&","")
    declarationline=declarationline.replace("const","")
    declarationline=declarationline.replace("<TReal>","")
    declarationline=declarationline.replace("TReal","ai_real")
    declarationline=declarationline.replace("bool ","boolean ")
    return result + declarationline

def clean_body(body=""):
    body=body.replace("static_cast<TReal>","new ai_real")
    body=body.replace("TReal ","ai_real ")
    body=body.replace("<TReal>","")
    body=body.replace("const ","")
    return body.replace("->",".").replace("::",".")

def fetch_declaration_line(method=""):
    return method[:method.index("{")]

def fetch_method_body(method=""):
    return method[method.index("{"):method.rindex("}")+1]

def run():
    import time
    track=""
    while True:
        #time.sleep(0.5)
        print("Waiting for input:")
        data='\n'.join(iter(raw_input, ""))
        dec=fetch_declaration_line(data)
        body=fetch_method_body(data)
        decr=clean_class_method_declaration(dec)
        bodyr=clean_body(body)
        pyperclip.copy(decr+bodyr)
        print("done!")
        #if pyperclip.paste()!=track:
            #print("Processing...")
            #track=pyperclip.paste()
            #todo=track.split("\n")[0]
            #remainder="\n".join(track.split("\n")[1:])
            #result=clean_class_method_declaration(todo)
            #pyperclip.copy(result+remainder)
            #track=result+remainder
            
