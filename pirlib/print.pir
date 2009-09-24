.sub 'print_bool'
	.param int b
	if b goto truth
	print '#f'
	.return()
truth:
	print '#t'
	.return()
.end
