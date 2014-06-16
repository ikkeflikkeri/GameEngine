#version 330
#include "lighting.fsh"

uniform PointLight R_pointLight;

vec4 calculateLight(vec3 normal, vec3 worldPos)
{
	return calcPointLight(R_pointLight, normal, worldPos);
}

#include "lightingMain.fsh"