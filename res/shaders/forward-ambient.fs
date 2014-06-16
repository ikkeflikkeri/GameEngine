#version 330

in vec2 texPos0;

uniform vec3 R_ambient;
uniform sampler2D diffuse;

void main()
{
	gl_FragColor = texture2D(diffuse, texPos0.xy) * vec4(R_ambient, 1);
}