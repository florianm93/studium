jmp start

fak:
mul ebx
inc ebx
cmp ecx, ebx
jne fak
ret 

start:
mov ecx, 5
mov ebx, 1
mov eax, ecx
jmp fak