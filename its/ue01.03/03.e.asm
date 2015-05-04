jmp start

ret_a:
mov edx, ebx
jmp end

ret_b:
mov edx, ecx
jmp end

set_a:
mov eax, ebx
sub eax, ecx
mov ebx, eax
jmp euklid

set_b:
mov eax, ecx
sub eax, ebx
mov ecx, eax
jmp euklid


euklid:
cmp ebx, 0
je ret_b
e_loop:
    cmp ecx, 0
    je ret_b
    cmp ebx, ecx
    jg set_a
    jle set_b
    ret
jmp set_a
ret
    
start:
mov ebx, 1071
mov ecx, 1029
jmp euklid
jmp end

end:
nop