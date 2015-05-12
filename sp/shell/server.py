from SimpleXMLRPCServer import SimpleXMLRPCServer

class Funcs:
        def add(self, x, y):
                return x + y 
        def sub(self, x, y):
                return x - y

server = SimpleXMLRPCServer(("localhost", 8000))
server.register_instance(Funcs())
server.serve_forever()

