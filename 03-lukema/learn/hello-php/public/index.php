<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="description" content="Hello AngularJs App" />
    <meta name="keywords" content="Hello AngularJS" />
    <meta content="no-cache" http-equiv="Cache-Control" />
    <meta content="no-cache" http-equiv="Pragma" />
    <meta http-equiv="EXPIRES" content="0" />
    <!-- X-UA-Compatible: tells the Internet Explorer to display the page in the highest mode available -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- viewport: to enable touch zooming and ensure proper rendering on mobile devices -->
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <title>hello-php</title>
</head>

<body>
    <?php echo "Hello, World!";?>

    <?php
		   # This is a comment, and
		   # This is the second line of the comment
		   
		   // This is a comment too. Each style comments only		   
		   print "An example with single line comments";
	  
		  # First Example
		  $variable = 22;
		   print <<<END
			This uses the "here document" syntax to output
			multiple lines with $variable interpolation. Note
			that the here document terminator must appear on a www
			line with just a semicolon no extra whitespace!
			END;
		   
			print "\n";

		   # Second Example
		   print "This spans
		   multiple lines. The newlines will be
		   output as well";
	   
	  ?>

</body>

</html>