#!/usr/bin/env python

import hashlib

message = hashlib.md5()
book = ["matse", "MATSE", "Matse", "mAtse", "maTse", "matSe", "matsE"]

for word in book:
    message.update(word)
    hexvalue = list(message.hexdigest())

    hashstr = ""
    for v in hexvalue:
        hashstr += str((int(hex(ord(v)), 16)&0xff) + 0x100)[1]
    print word, hashstr
