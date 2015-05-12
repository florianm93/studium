import xmlrpclib

s = xmlrpclib.Server("http://localhost:8000")
#print s.system.listMethods()

print s.add(2, 3)
print s.sub(5, 2)
