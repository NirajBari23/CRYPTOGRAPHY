const crypto = require('crypto');
const net = require('net');
const readline = require('readline');

const server = net.createServer((socket) => {
  let data = '';

  socket.on('data', (chunk) => {
    data += chunk;
  });

  socket.on('end', () => {
    const [encryptedText, ivHex] = data.split('\n').map(line => line.trim());
    const iv = Buffer.from(ivHex, 'hex');

    const rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout
    });

    rl.question('Enter the key to decrypt the message: ', (keyHex) => {
      const key = Buffer.from(keyHex, 'hex');

      try {
        // Decrypt the ciphertext
        const decipher = crypto.createDecipheriv('aes-256-cbc', key, iv);
        let decrypted = decipher.update(encryptedText, 'hex', 'utf8');
        decrypted += decipher.final('utf8');

        console.log('Decrypted text:', decrypted);
      } catch (err) {
        console.error('Decryption failed:', err.message);
      }

      rl.close();
    });
  });
});

server.listen(1337, '127.0.0.1', () => {
  console.log('Server listening on port 1337');
});