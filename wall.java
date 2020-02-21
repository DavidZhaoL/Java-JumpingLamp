import com.jogamp.opengl.*;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

public class wall extends Model{
    private Vec3 scale;
    private Vec3 translate;
    private Model[] myWall;
    private float zPosition=4.5f;

    public wall(GL3 gl, Camera camera, Light light, Light light1, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2, Vec3 scale, Vec3 translate)
    {
        super(gl,camera,light,light1,shader,material,modelMatrix,mesh,textureId1,textureId2);
        myWall=new Model[20];//I can't use myWall until I assign it!!!!!
        setwall(gl);
    }

    public void setwall(GL3 gl)
    {
        int i;
        Mat4 modelMatrix1;
        for(i=0;i<6;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(-10+(i*4), 2, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

        for(i=6;i<8;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(-10+((i-6)*4), 2+4, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

        for(i=8;i<10;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(10-((i-8)*4), 2+4, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

        for(i=10;i<12;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(-10+((i-10)*4), 2+8, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

        for(i=12;i<14;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(10-((i-12)*4), 2+8, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

        for(i=14;i<20;i++) {
            modelMatrix1 = Mat4.multiply(Mat4Transform.translate(-10+((i-14)*4), 2+12, -4f+zPosition), modelMatrix);
            myWall[i] = new Model(gl, camera, light, light1, shader, material, modelMatrix1, mesh, textureId1);
        }

    }

    public void renderwall(GL3 gl)
    {

        for(int i=0;i<myWall.length;i++)
        {
            myWall[i].render(gl);
        }
    }

}
