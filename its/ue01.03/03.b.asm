mov eax, 4
mov ebx, 3

swap:
    mov ecx, eax
    mov eax, ebx
    mov ebx, ecx
    mov ecx, 0
    ret
    
    
call swap
;jmp exit

;exit:
;nop

