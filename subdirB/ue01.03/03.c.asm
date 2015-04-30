jmp start

count:
push ebx
push ecx
mov ecx, 0
count_loop:
mov ebx, 1
and ebx, eax
add ecx, ebx
shr eax, 1
cmp eax, 0
jne count_loop
mov eax, ecx
pop ecx
pop ebx
ret

start:
mov eax, 25
call count