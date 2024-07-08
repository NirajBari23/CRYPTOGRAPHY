const readline = require('readline');
const crypto = require('crypto');
const net = require('net');

// AES-256-CBC requires a 32-byte key and a 16-byte IV
const key = crypto.randomBytes(32);
const iv = crypto.randomBytes(16);

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

rl.question('Enter text to encrypt and send: ', (text) => {
  // Encrypt the text
  const cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
  let encrypted = cipher.update(text, 'utf8', 'hex');
  encrypted += cipher.final('hex');

  // Encode the IV as a hex string
  const ivHex = iv.toString('hex');

  // Create a socket connection to the receiver
  const client = new net.Socket();
  client.connect(1337, '127.0.0.1', () => {
    console.log('Connected to server');
    client.write(encrypted + '\n');
    client.write(ivHex + '\n');
    client.end();
  });

  client.on('close', () => {
    console.log('Connection closed');
    console.log('The key to decrypt the message is:', key.toString('hex'));
  });

  rl.close();
});