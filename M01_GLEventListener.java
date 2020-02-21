import gmaths.*;

import java.nio.*;
import java.security.cert.X509Certificate;
import java.util.NavigableMap;

import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
import java.util.Random;
import javax.jws.WebParam;
import javax.naming.Name;
import javax.swing.*;

public class M01_GLEventListener implements GLEventListener {

  private static final boolean DISPLAY_SHADERS = false;

  public M01_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(0f,8f,18f));
    this.camera.setTarget(new Vec3(0f,5f,0f));
    Random rand=new Random();
  }

  private double startTime,startTime1,startTimeBackGround;

  private Lamp lamp;
  private float randAngle;
  private int NUM_RANDOMS = 1000;
  private float[] randoms;
  private Camera camera;
  private Material changingMaterial;

  private Model floor,bulb,fakeSkyBox,obj1,obj2,objOnTable,objOnTable1,objOnTable2;
  private wall mywall;
  private table myTable;
  private Light light,light1,lamplight;



  private float xPosition = -2;
  private float yPosition=4.1f;
  private float zPosition=4.5f;


  private float poseAngle=0;


  private int flag=0;
  private int flag1=0;

  private float rotateAllAngleStart = 12, rotateAllAngle = rotateAllAngleStart;
  private float rotateUpperAngleStart = -12, rotateUpperAngle = rotateUpperAngleStart;
  private boolean lampSwich=false;
  private boolean lampClose=false;
  // **************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
    startTimeBackGround=getSeconds();
    //lamp.setStartTime(startTime);
    //lamp.setStartTime1(startTime1);
  }

  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    disposeModels(gl);
  }

  // ***************************************************
  /* click listener method
   */

  public void startAnimation()
  {
    startTime=getSeconds();
    lamp.setRotateBeforeJump(true);
    Random rand=new Random((int)getSeconds());
    randAngle=-135+rand.nextInt(270);
    lamp.setStartTime(startTime);
    lamp.setRandAngle(randAngle);
  }

  public void randomPose()
  {
    startTime1=getSeconds();
    lamp.setRandRotate(true);
    Random rand=new Random((int)getSeconds());
    poseAngle=10+rand.nextInt(30);
    if(flag!=0)
    {
      poseAngle=-poseAngle;
      //System.out.println("flag=1");
      flag=0;
    }
    else{
      flag=1;
    }
    lamp.setRandRotate(true);
    lamp.setStartTime1(startTime1);
    lamp.setPoseAngle(poseAngle);

  }

  public void openLamp()
  {
    lampSwich=true;

  }

  public void closeLamp()
  {
    lampSwich=false;
  }

  public void lightControl(GL3 gl)
  {
    lamplight=new Light(gl);
    lamplight.setCamera(camera);
  }
///////////////////////////////////////////////////////////

  private void disposeModels(GL3 gl) {
      floor.dispose(gl);

    bulb.dispose(gl);
    light.dispose(gl);
    light1.dispose(gl);
  }

  //////////////////////////////////////////////////////////////////////////////////////


  public void initialise(GL3 gl) {
    createRandomNumbers();
    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/chequerboard.jpg");
    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/cloud.jpg");
    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/mar0kuu2.jpg");
    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/sinary_bhs35wjr.jpg");
    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/jup0vss1.jpg");
    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/hqdefault.jpg");
    int[] textureId7 = TextureLibrary.loadTexture(gl, "textures/0006675-thick-line-paper.jpg");

    light = new Light(gl);
    light.setCamera(camera);

    light1=new Light(gl);
    light1.setCamera(camera);

    lamplight=new Light(gl);
    lamplight.setCamera(camera);

    //floor
    Mesh m = new Mesh(gl, TwoTriangles.vertices, TwoTriangles.indices);
    Shader shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_wall.txt");
    Material  material = new Material(new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.8f, 0.8f, 0.8f), 35.0f);
    Mat4 modelMatrix = Mat4Transform.scale(32,1f,32);
    floor = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId0);

    //make the wall object
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_wall.txt");
    material = new Material(new Vec3(0.6f, 0.6f, 0.6f), new Vec3(0.6f, 0.6f, 0.6f), new Vec3(0.8f, 0.8f, 0.8f), 35.0f);

    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90),Mat4Transform.scale(6,1f,6));//this is scaled by the center(0,0,0),y+2 then we can see the whole obj
    mywall=new wall(gl, camera, light, lamplight, shader, material, modelMatrix, m, textureId5,textureId5,null,null);

    //table
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
      material = new Material(new Vec3(0.6f, 0.6f, 0.6f), new Vec3(0.6f, 0.6f, 0.6f), new Vec3(0.8f, 0.8f, 0.8f), 35.0f);
    myTable=new table(gl, camera, light,lamplight, shader, material, m, textureId2,null,null,null);

    //object on table
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-5,4.3f,3),Mat4Transform.scale(2,0.1f,2));
    obj1 = new Model(gl, camera, light,lamplight, shader, material, modelMatrix, m, textureId2);

    objOnTable1=new Model(gl, camera, light,lamplight, shader, material, modelMatrix, m, textureId6);

    modelMatrix = Mat4.multiply(Mat4Transform.translate(5,5f,3f),Mat4Transform.scale(1,2,1));
    obj2 = new Model(gl, camera, light,lamplight, shader, material, modelMatrix, m, textureId4);

    objOnTable=new objOnTable(gl, camera, light,lamplight, shader, material, modelMatrix, m, textureId1,null);

    //this is the outside environment
    shader = new Shader(gl, "vs_cube_04.txt", "fs_background.txt");
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 20.0f);
    modelMatrix = Mat4.multiply( Mat4Transform.translate(0,6,-12+zPosition),Mat4Transform.scale(16,16,1));
    fakeSkyBox = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId3);


    // this is the lamp bulb
      shader = new Shader(gl, "vs_sphere_04.txt", "fs_cube_no_texture.txt");
      material = new Material(new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.8f, 0.8f, 0.8f), 35.0f);
      modelMatrix = Mat4.multiply(Mat4Transform.scale(2,2,2), Mat4Transform.translate(0,0.5f,0));
      modelMatrix = Mat4.multiply(Mat4Transform.translate(0,0,0), modelMatrix);

    bulb = new Model(gl, camera, light,lamplight, shader, material, modelMatrix, m,textureId7);




    //set the material of bulb
      material = new Material(new Vec3(0.1f, 0.1f, 0.1f), new Vec3(0.1f, 0.1f, 0.1f), new Vec3(0.9f, 0.9f, 0.9f), 35.0f);
      lamplight.setMaterial(material);


    // another object on the table
    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04.txt");

    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply( Mat4Transform.translate(-6,4.3f,6),Mat4Transform.scale(0.2f,0.2f,2.5f));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,0,0), modelMatrix);
    objOnTable2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId3, textureId4);

    lamp=new Lamp(obj2,obj1,bulb,objOnTable2,xPosition,yPosition,zPosition);
  }

  // to control the time
  private double daytime=0;
  public void changeBackground()// background becoming darker
  {
    float timeChange=(float)(getSeconds()-startTimeBackGround);
    timeChange=timeChange*0.1f;
    Vec3 vec=new Vec3(1.0f-timeChange, 1.0f-timeChange, 1.0f-timeChange);
    if(timeChange<0.95) {
      changingMaterial = new Material(vec, vec, vec, 20.0f);
      fakeSkyBox.setMaterial(changingMaterial);
    }
    else{
      while(daytime<4)
      {
        daytime+=getSeconds()-startTimeBackGround;
      }
      daytime=0;
      startTimeBackGround=getSeconds();
      flag1=1;
    }
  }

  public void changeBackground1()//background becoming lighter
  {
    float timeChange=(float)(getSeconds()-startTimeBackGround);
    timeChange=timeChange*0.09f;
    Vec3 vec=new Vec3(0.05f+timeChange, 0.05f+timeChange, 0.05f+timeChange);
    if(timeChange<0.95) {
      changingMaterial = new Material(vec, vec, vec, 20.0f);
      fakeSkyBox.setMaterial(changingMaterial);
    }
    else{
      while(daytime<4)
      {
        daytime+=getSeconds()-startTimeBackGround;
      }
      daytime=0;
      startTimeBackGround=getSeconds();
      flag1=0;
    }
  }

  //reset the lamp
  public void reset()
  {
    lamp.reset();
  }

  private void render(GL3 gl) {

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    if(lampSwich) {
        lamplight.setPosition(getLampLightPosition());
        lamplight.render(gl);

    }
    else//close light
    {
        lamplight.setPosition(closeGetLightPosition1());
        lamplight.render(gl);
    }


    //the outside environment is changing like the day and night
    if(flag1==0)
    {
    changeBackground();
    }
    else{
      changeBackground1();
    }


    light.setPosition(getLightPosition());  // changing light position each frame
    light.render(gl);

    //light1.setPosition(getLightPosition1());
    //light1.render(gl);

    floor.render(gl);
    mywall.renderwall(gl);

    myTable.renderTable(gl);

    objOnTable1.render(gl);

    objOnTable.render(gl);

    objOnTable2.render(gl);
    fakeSkyBox.render(gl);
    
    
    if(lamp.getRotateBeforeJump())
    {
      lamp.rotate();
    }

    if(lamp.getJump()) {
      lamp.updateBranches();
    }

    if(lamp.getRandRotate())
    {
      lamp.updateBranchesRan();
    }

    lamp.twoBranchRoot.draw(gl);
  }

  // The light's postion is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 8.0f;//*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float y = 10.7f;
    float z = 5.0f;//*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
    return new Vec3(x,y,z);

   
  }

  //a new light position 
    private Vec3 getLightPosition1() {

        float x = -8.0f;
        float y = 10.7f;
        float z = 5.0f;
        return new Vec3(x,y,z);
    }

    //close light
    private Vec3 closeGetLightPosition1() {

        float x = -8.0f;
        float y = -10.7f;
        float z = -5.0f;
        return new Vec3(x,y,z);
    }

    //link the light with the bulb object
  private Vec3 getLampLightPosition()
  {
    Mat4 bulbTransform=lamp.getBulbTransform();
    float[] a=new float[4];

    Vec4 vec4=new Vec4();
    a[0]=vec4.x;
    a[1]=vec4.y;
    a[2]=vec4.y;
    a[3]=vec4.w;

    Vec3 vec=new Vec3();

    for(int j=0;j<4;j++)
    {
        vec.x+=bulbTransform.values[0][j]*a[j];
    }

    for(int j=0;j<4;j++)
    {
      vec.y+=bulbTransform.values[1][j]*a[j];
    }

    for(int j=0;j<4;j++)
    {
      vec.z+=bulbTransform.values[2][j]*a[j];
    }

    return vec;
  }


  // ***************************************************
  /* TIME
   */


  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  // ***************************************************
  /* An array of random numbers
   */

  private void createRandomNumbers() {
    randoms = new float[NUM_RANDOMS];
    for (int i=0; i<NUM_RANDOMS; ++i) {
      randoms[i] = (float)Math.random();
    }
  }


}