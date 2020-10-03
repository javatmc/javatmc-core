# SCPI Send command

Run `scpi-send` to send SCPI commands to an instrument.

## Options

Socket specification.

`--interactive -i`: Commands are provided via `stdin`, responses are printed to `stdout`

`--commands [COMMANDS..]`: Commands are provided as arguments

`--script <scpi file>`: Commands are provided via a SCPI file