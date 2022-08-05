import sys
import json

def printSomething(form):
    file = open(form)
    datas=json.load(file)

    arg1 = datas["form"]["arg1"]
    arg2 = datas["form"]["arg2"]
    arg3 = datas["form"]["arg3"]

    print( arg1, arg2+arg3 )



# printSomething("test.json")

if __name__ == "__main__":
   printSomething(sys.argv[2])
#    print(sys.argv[2])