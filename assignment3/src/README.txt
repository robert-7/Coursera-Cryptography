This summarizes the files and what they are.

cbc-encrypt.c contains code for CBC encryption (with padding) using AES,
as discussed in class. The file is provided for your convenience,
though you actually don't need to use it as long as you trust it was
implemented correctly. (If desired, you can compile it using the
provided Makefile. The aes* files implement the block cipher AES,
but you can treat AES as a black box for this homework and should
not need to look at those files at all.)

cbc-encrypt.c was used to encrypt an unknown message using an unknown key k,
with the resulting ciphertext written to challenge-ciphertext.txt.

server.c contains wrapper code for a server that receives a ciphertext
from a client, decrypts it using the (same) unknown key k, and returns
a result indicating whether decryption succeeds or fails (i.e., whether
the padding was correct). It calls the cbcdec function provided in
cbc-decrypt.c.  These files are provided for your convenience, but
again you do not actually need to look at them as long as you trust that
they are implemented correctly.

hex2ascii.c simply converts hex to ASCII.

sample.* contains code (in C, Java, Ruby, and Python) demonstrating how
you can write code that sends a ciphertext to the server and receives
back a result. This code relies on the oracle.* files, but you do not
need to look at those files.

If you understand the details of the padding oracle attack, the only
file you need to look at is sample.* (in your language of your choice).
