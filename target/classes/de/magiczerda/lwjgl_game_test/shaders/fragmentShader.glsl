#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

float ambientLighting = 0.2;

void main(void) {
	
	vec3 unitNormal = normalize(surfaceNormal);	//we have to normalize both vectors before using the dot product
	vec3 unitLightVector = normalize(toLightVector);
	
	vec3 unitToCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitToCameraVector);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	
	float unsignedDot = dot(unitNormal, unitLightVector);
	float brightness = max(unsignedDot, ambientLighting);	//the dot product might be negative, so we want to cull that
	vec3 diffuse = brightness * lightColor;
	
	out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoords) + vec4(finalSpecular, 1.0);
	
}