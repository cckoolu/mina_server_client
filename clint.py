import socket
import json
import time

s = socket.socket()

ip_port = ("192.168.2.38", 6000)
s.connect(ip_port)

while True:
    data = input()
    if data != "quit":
        s.send(data.encode() + b'\n')

        # 4、接收对方发送过来的数据，最大接收1024个字节
        receiveData = s.recv(1024).decode()
        print(receiveData)
    else:
        s.send(data.encode() + b'\n')
        receiveData = s.recv(1024).decode()
        print(receiveData)
        break
