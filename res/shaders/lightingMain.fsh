void main()
{
	gl_FragColor = texture2D(diffuse, texPos0.xy) * calculateLight(normalize(normal0), worldPos0);
}