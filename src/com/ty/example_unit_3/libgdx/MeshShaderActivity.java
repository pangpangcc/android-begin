package com.ty.example_unit_3.libgdx;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * 
 * @author tangyong
 *
 */
public class MeshShaderActivity extends AndroidApplication{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MeshShaderListener listener = new MeshShaderListener();
		initialize(listener, true);
	}
	
	class MeshShaderListener implements ApplicationListener{
		
		ShaderProgram shader;
		Mesh mesh;
		Texture texture;
		Matrix4 matrix = new Matrix4();

		@Override
		public void create() {
			
			String vertexShader = "attribute vec4 a_position;    \n" + "attribute vec4 a_color;\n" + "attribute vec2 a_texCoord0;\n"
					+ "uniform mat4 u_worldView;\n" + "varying vec4 v_color;" + "varying vec2 v_texCoords;"
					+ "void main()                  \n" + "{                            \n" + "   v_color = vec4(1, 1, 1, 1); \n"
					+ "   v_texCoords = a_texCoord0; \n" + "   gl_Position =  u_worldView * a_position;  \n"
					+ "}                            \n";
			
				String fragmentShader = "#ifdef GL_ES\n" + "precision mediump float;\n" + "#endif\n" + "varying vec4 v_color;\n"
					+ "varying vec2 v_texCoords;\n" + "uniform sampler2D u_texture;\n" + "void main()                                  \n"
					+ "{                                            \n" + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
					+ "}";

				shader = new ShaderProgram(vertexShader, fragmentShader);
				if (shader.isCompiled() == false) {
					Gdx.app.log("ShaderTest", shader.getLog());
					Gdx.app.exit();
				}
				// 4 个表示顶点数量，6表示索引数目 
				mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
				mesh.setVertices(new float[] {
						//第一个顶点
						-0.5f, -0.5f, 0,
						1,1,1,1, 
						0,1,
						
						//第二个顶点
						0.5f,-0.5f, 0,
						1, 1, 1, 1,
						1,1,
						
						//第三个顶点
						0.5f, 0.5f, 0,
						1, 1, 1,1, 
						1,0,
						
						//第四个顶点
						-0.5f, 0.5f, 0,
						1,1,1,1, 
						0, 0
					});
				mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
				texture = new Texture(Gdx.files.internal("data/cube_simple.png"));
			
		}

		@Override
		public void dispose() {
			mesh.dispose();
			texture.dispose();
			shader.dispose();
			
		}

		Vector3 axis = new Vector3(1, 0, 0);
		float angle = 0;
		
		@Override
		public void pause() {
			
		}

		@Override
		public void render() {
			angle += Gdx.graphics.getDeltaTime() * 45;
			matrix.setToRotation(axis, angle);

			Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
			Gdx.gl20.glEnable(GL10.GL_BLEND);
			Gdx.gl20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			texture.bind();
			shader.begin();
			shader.setUniformMatrix("u_worldView", matrix);
			shader.setUniformi("u_texture", 0);
			mesh.render(shader, GL10.GL_TRIANGLES);
			shader.end();
			
		}

		@Override
		public void resize(int arg0, int arg1) {
			
		}

		@Override
		public void resume() {
			
		}
		
	}

}
