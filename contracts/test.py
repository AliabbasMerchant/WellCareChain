import xmlrpclib
import socket

def _get_rpc():
    a = xmlrpclib.ServerProxy('http://192.168.43.23:8545')
    try:
        print "in"
        a._()   # Call a fictive method.
    except xmlrpclib.Fault:
        print "connected to the server and the method doesn't exist which is expected."
    except socket.error:
        print "Not connected ; socket error mean that the service is unreachable."
        return False, None

    # Just in case the method is registered in the XmlRPC server
    return True, a

connected, server_proxy = _get_rpc()
if not connected:
    print "Failed to connect"