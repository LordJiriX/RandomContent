package io.github.lordjirix.randomcontent.api.block;

public interface IRecipeRunnable {
  int getTimeToRunRecipe();

  int getCurrentRunTime();

  int getRFPerTick();
}
