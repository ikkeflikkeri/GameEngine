layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec3 normal;

out vec2 texPos0;
out vec3 normal0;
out vec3 worldPos0;

uniform mat4 T_model;
uniform mat4 T_modelViewProjection;

void main()
{
	texPos0 = texPos;
	normal0 = (T_model * vec4(normal, 0.0)).xyz;
	worldPos0 = (T_model * vec4(position, 1.0)).xyz;
	gl_Position = T_modelViewProjection * vec4(position, 1.0);
}