#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texPos;

out vec2 texPos0;

uniform mat4 T_modelViewProjection;

void main()
{
	texPos0 = texPos;
	gl_Position = T_modelViewProjection * vec4(position, 1.0);
}