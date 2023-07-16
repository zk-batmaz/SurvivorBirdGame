package com.qbra.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture skeleton;
	Texture stone1;
	Texture stone2;
	Texture stone3;

	float skeletonX = 0;
	float skeletonY = 0;
	int gameState = 0;
	float velocity = 0;
	float stoneVelocity = 5;
	Random random;
	int score = 0;
	int enemyScore = 0;
	BitmapFont scoreFont;
	BitmapFont gameOverFont;

	Circle skeletonCircle;
	Circle[] stoneCircle1;
	Circle[] stoneCircle2;
	Circle[] stoneCircle3;

	//ShapeRenderer shapeRenderer;

	int numberOfStones = 4;
	float[] enemyX = new float[numberOfStones];
	float[] enemyOffSet1 = new float[numberOfStones];
	float[] enemyOffSet2 = new float[numberOfStones];
	float[] enemyOffSet3 = new float[numberOfStones];

	float distance = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();

		background = new Texture("background.png");
		skeleton = new Texture("skeleton.png");
		stone1 = new Texture("enemy.png");
		stone2 = new Texture("enemy.png");
		stone3 = new Texture("enemy.png");

		skeletonX = Gdx.graphics.getWidth()/4;
		skeletonY = Gdx.graphics.getHeight()/3;

		distance = Gdx.graphics.getWidth() / 2;
		random= new Random();

		skeletonCircle = new Circle();
		stoneCircle1 = new Circle[numberOfStones];
		stoneCircle2 = new Circle[numberOfStones];
		stoneCircle3 = new Circle[numberOfStones];

		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		scoreFont.getData().setScale(4);

		gameOverFont = new BitmapFont();
		gameOverFont.setColor(Color.BLACK);
		gameOverFont.getData().setScale(6);

		//shapeRenderer = new ShapeRenderer();

		for(int i = 0; i<numberOfStones; i++)
		{
			enemyOffSet1[i] = 1000;
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = 50;

			enemyX[i] = Gdx.graphics.getWidth() - stone1.getWidth()/2 + (i * distance);

			stoneCircle1[i] = new Circle();
			stoneCircle2[i] = new Circle();
			stoneCircle3[i] = new Circle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if( gameState == 1)
		{
			if(enemyX[enemyScore] < Gdx.graphics.getWidth()/4)
			{
				score++;
				if(enemyScore < numberOfStones - 1)
				{
					enemyScore++;
				}
				else
				{
					enemyScore = 0;
				}
			}
			if(Gdx.input.justTouched())
			{
				velocity = -15;
			}

			for(int i = 0; i<numberOfStones; i++)
			{
				if(enemyX[i] < Gdx.graphics.getWidth() / 14)
				{
					enemyX[i] = enemyX[i] + numberOfStones * distance;

					enemyOffSet1[i] = 1000;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = 50;
				}
				else
				{
					enemyX[i] = enemyX[i] - stoneVelocity;
				}

				batch.draw(stone1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet1[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
				batch.draw(stone2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
				batch.draw(stone3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);

				stoneCircle1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);
				stoneCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);
				stoneCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);

			}

			if(skeletonY > 0 )
			{
				velocity = (float) (velocity + 1.5);
				skeletonY = skeletonY - velocity;
			}
			else
			{
				gameState = 2;
			}

		}
		else if (gameState == 0)
		{
			if(Gdx.input.justTouched())
			{
				gameState = 1;
			}
		}
		else if (gameState == 2)
		{
			gameOverFont.draw(batch,"Game Over", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			if(Gdx.input.justTouched())
			{
				gameState = 1;
				skeletonY = Gdx.graphics.getHeight()/3;

				for(int i = 0; i<numberOfStones; i++)
				{
					enemyOffSet1[i] = 1000;
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = 50;

					enemyX[i] = Gdx.graphics.getWidth() - stone1.getWidth()/2 + (i * distance);

					stoneCircle1[i] = new Circle();
					stoneCircle2[i] = new Circle();
					stoneCircle3[i] = new Circle();
				}
				velocity = 0;
				score = 0;
				enemyScore = 0;
			}
		}

		batch.draw(skeleton, skeletonX, skeletonY, Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight()/10);

		scoreFont.draw(batch,String.valueOf(score),100,200);
		batch.end();

		skeletonCircle.set(skeletonX + Gdx.graphics.getWidth() / 36,skeletonY + Gdx.graphics.getWidth() / 30,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(skeletonCircle.x, skeletonCircle.y, skeletonCircle.radius);

		for(int i = 0; i < numberOfStones; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 28);

			if(Intersector.overlaps(skeletonCircle,stoneCircle1[i]) || Intersector.overlaps(skeletonCircle, stoneCircle2[i]) || Intersector.overlaps(skeletonCircle,stoneCircle3[i]))
			{
				gameState = 2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
