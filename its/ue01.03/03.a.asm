jmp start

write:
    push ebx
    add ebx, 2
    inc ecx
    cmp ecx, eax
    jne write
    ret

start: 
    mov eax, 1000 ; length of memory
    mov ebx, 1 ;starting value
    mov ecx, 0 ;counter
    jmp write