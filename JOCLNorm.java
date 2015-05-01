/**
   Class to compute norm value of vectors based on the given input text with JOCL
   @author Shruti
   @version 1.01 2015/3/9
*/
import static org.jocl.CL.*;
import org.jocl.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.*;
import java.util.StringTokenizer;
import java.io.*;
import java.lang.*;

/**
  Class to compute the norm of vectors
 */
public class JOCLNorm
{
    /**
     The kernel code of the OpenCL program to find squares of the vector.
		@param a,b and c to find the squares of vectors.
		The square of vectors is stored in c[gid].
	 */
    private static String programSource0 =
        "__kernel void "+
        "sampleKernel(__global const int *a,"+
        "             __global const int *b,"+
        "             __global int *c)"+
        "{"+
        "    int gid = get_global_id(0);"+
        "    c[gid] = a[gid] * b[gid];"+
        "}";
    
	/**
     The kernel code of the OpenCL program to find the sum of squares of the vector.
		@param a,c and output to find the sum of vectors.
		The sum of squares of vectors is stored in output[gid]
	 */
    private static String programSource1 = 
        "__kernel void sampleAdd(__global const int *a,"+
        "             __global const int *c,"+
        "             __global int *output)"+
        "{"+
        "    int gid = get_global_id(0);"+
		"    for (int i=0; i<50; i++) " +
        "        output[gid] += c[i];" +

        "}";
    
	/**
     The kernel code of the OpenCL program to find the square root of the sum of squares of the vector.
		@param a,output and rootOutput to find the root of vectors.
		The root of sum of squares of vectors is stored in rootOutput[gid]
	 */
    private static String programSource2 = 
        "__kernel void sampleRoot(__global const int *a,"+
        "             __global const int *output,"+
        "             __global float *rootOutput)"+
        "{"+
        "    int gid = get_global_id(0);"+
        "        rootOutput[gid] = sqrt((float)output[gid]);" +

        "}";
	
	 /**
       Driver method for JOCLNorm
       @param args  command line arguments array, args[0] should contain
       an text input file which contains the vectors on each line. 
	   Give the jar file path while executing the program.
     */
	 
    public static void main(String args[]) 
    {
        // Create a fileReader object to read the contents of file.
	    String DELIMITER = " ";
		int MINUS_ONE = -1;
		try
		{
		   String INPUT_FILE_NAME = args[0];
		   FileReader  reader = new FileReader(INPUT_FILE_NAME);
		   Scanner scanner = new Scanner(reader);
		  
		    /* Boolean that will become false when -1 is encountered and 
			 is then used to stop the reading process.*/
			 
		   boolean cont = true;
		   int numberarray[] = new int[30];
		   int indes = 0;
		   
		   // Loop to read the contents of file till it reaches the end of file.
           while(cont && scanner.hasNextLine()) 
           {
			   String line = scanner.nextLine();
			   StringTokenizer tokenizer = new StringTokenizer(line, DELIMITER);
			   while(cont && tokenizer.hasMoreTokens()) 
			   {
			      int number = Integer.parseInt(tokenizer.nextToken());
			      if(number == MINUS_ONE) 
				  {
				     cont = false;
                  } 
                  else 
		          {
			         numberarray[indes]=number;
			         indes ++;
		          }
		       }
	        }
	        int sized;
	        sized = indes;
			
			int n = indes;
			
			// Initialize the arrays
			int srcArrayA[] = new int[n];
			int srcArrayB[] = new int[n];
			int dstArray0[] = new int[n];
			int dstArray1[] = new int[n];
			float finaResult[] = new float[n];
			for (int i=0; i<n; i++)
			{
				srcArrayA[i] = numberarray[i];
				srcArrayB[i] = numberarray[i];
			}
			Pointer srcA = Pointer.to(srcArrayA);
			Pointer srcB = Pointer.to(srcArrayB);
			Pointer dst0 = Pointer.to(dstArray0);
			Pointer dst1 = Pointer.to(dstArray1);
			Pointer finalVal = Pointer.to(finaResult);

			/* The platform, device type and device number
			 that will be used */
			final int platformIndex = 0;
			final long deviceType = CL_DEVICE_TYPE_ALL;
			final int deviceIndex = 0;

			// Enable exceptions and subsequently omit error checks in this sample
			CL.setExceptionsEnabled(true);

			// Obtain the number of platforms
			int numPlatformsArray[] = new int[1];
			clGetPlatformIDs(0, null, numPlatformsArray);
			int numPlatforms = numPlatformsArray[0];

			// Obtain a platform ID
			cl_platform_id platforms[] = new cl_platform_id[numPlatforms];
			clGetPlatformIDs(platforms.length, platforms, null);
			cl_platform_id platform = platforms[platformIndex];

			// Initialize the context properties
			cl_context_properties contextProperties = new cl_context_properties();
			contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);
        
			// Obtain the number of devices for the platform
			int numDevicesArray[] = new int[1];
			clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray);
			int numDevices = numDevicesArray[0];
        
			// Obtain a device ID 
			cl_device_id devices[] = new cl_device_id[numDevices];
			clGetDeviceIDs(platform, deviceType, numDevices, devices, null);
			cl_device_id device = devices[deviceIndex];

			// Create a context for the selected device
			cl_context context = clCreateContext(
            contextProperties, 1, new cl_device_id[]{device}, 
            null, null, null);
        
			// Create a command-queue for the selected device
			cl_command_queue commandQueue = 
            clCreateCommandQueue(context, device, 0, null);

			// Allocate the memory objects for the input- and output data
			cl_mem memObjects[] = new cl_mem[5];
			memObjects[0] = clCreateBuffer(context, 
				CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
				Sizeof.cl_int * n, srcA, null);
			memObjects[1] = clCreateBuffer(context, 
				CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
				Sizeof.cl_int * n, srcB, null);
			memObjects[2] = clCreateBuffer(context, 
				CL_MEM_READ_WRITE, 
				Sizeof.cl_int * n, null, null);	
			memObjects[3] = clCreateBuffer(context, 
				CL_MEM_READ_WRITE, 
				Sizeof.cl_int * n, null, null);
			memObjects[4] = clCreateBuffer(context, 
				CL_MEM_READ_WRITE, 
				Sizeof.cl_int * n, null, null);

			// Create the program from the source code
			cl_program program0 = clCreateProgramWithSource(context,
				1, new String[]{ programSource0 }, null, null);
        
			cl_program program1 = clCreateProgramWithSource(context,
				1, new String[]{ programSource1 }, null, null);

			cl_program program2 = clCreateProgramWithSource(context,
				1, new String[]{ programSource2}, null, null);

			// Build the programs
			clBuildProgram(program0, 0, null, null, null, null);
			clBuildProgram(program1, 0, null, null, null, null);
			clBuildProgram(program2, 0, null, null, null, null);
		
			// Create the kernel
			cl_kernel kernel0 = clCreateKernel(program0, "sampleKernel", null);
			cl_kernel kernel1 = clCreateKernel(program1, "sampleAdd", null);
			cl_kernel kernel2 = clCreateKernel(program2, "sampleRoot", null);
			
			//Computing the time
			long before = System.nanoTime();
			
			// Set the arguments for the kernel
			clSetKernelArg(kernel0, 0, Sizeof.cl_mem, Pointer.to(memObjects[0]));
			clSetKernelArg(kernel0, 1, Sizeof.cl_mem, Pointer.to(memObjects[1]));
			clSetKernelArg(kernel0, 2, Sizeof.cl_mem, Pointer.to(memObjects[2]));
			
			clSetKernelArg(kernel1, 0, Sizeof.cl_mem, Pointer.to(memObjects[0]));
			clSetKernelArg(kernel1, 1, Sizeof.cl_mem, Pointer.to(memObjects[2]));
			clSetKernelArg(kernel1, 2, Sizeof.cl_mem, Pointer.to(memObjects[3]));
		
			clSetKernelArg(kernel2, 0, Sizeof.cl_mem, Pointer.to(memObjects[0]));
			clSetKernelArg(kernel2, 1, Sizeof.cl_mem, Pointer.to(memObjects[3]));
			clSetKernelArg(kernel2, 2, Sizeof.cl_mem, Pointer.to(memObjects[4]));
		
			// Set the work-item dimensions
			long globalWorkSize[] = new long[]{n};
			
			cl_event kernelEvent0 = new cl_event();
			clEnqueueNDRangeKernel(commandQueue, kernel0, 1, null,
				globalWorkSize, null, 0, null, kernelEvent0);

			cl_event kernelEvent1 = new cl_event();
			clEnqueueNDRangeKernel(commandQueue, kernel1, 1, null,
				globalWorkSize, null, 0, null, kernelEvent1);

			cl_event kernelEvent2 = new cl_event();
			clEnqueueNDRangeKernel(commandQueue, kernel2, 1, null,
				globalWorkSize, null, 0, null, kernelEvent2);

			// Read the output data
			cl_event readEvent0 = new cl_event();
			clEnqueueReadBuffer(commandQueue, memObjects[2], CL_TRUE, 0,
				n * Sizeof.cl_int, dst0, 0, null, readEvent0);
        
			cl_event readEvent1 = new cl_event();
			clEnqueueReadBuffer(commandQueue, memObjects[3], CL_TRUE, 0,
				n * Sizeof.cl_int, dst1, 0, null, readEvent1);
		
			cl_event readEvent2 = new cl_event();
			clEnqueueReadBuffer(commandQueue, memObjects[4], CL_TRUE, 0,
				n * Sizeof.cl_int, finalVal, 0, null, readEvent1);
		
			// Wait for the the events, i.e. until the results are read
			CL.clWaitForEvents(2, new cl_event[]{readEvent0, readEvent1, readEvent2});
			long after = System.nanoTime();
		
			float totalDurationMs = (after-before);
			System.out.println("Total duration: "+totalDurationMs);
        
				
			// Release kernel, program, and memory objects
			clReleaseMemObject(memObjects[0]);
			clReleaseMemObject(memObjects[1]);
			clReleaseMemObject(memObjects[2]);
			clReleaseMemObject(memObjects[3]);
			clReleaseMemObject(memObjects[4]);
			clReleaseKernel(kernel0);
			clReleaseKernel(kernel1);
			clReleaseKernel(kernel2);
			clReleaseProgram(program0);
			clReleaseProgram(program1);
			clReleaseProgram(program2);
			clReleaseCommandQueue(commandQueue);
			clReleaseContext(context);
        
			//Printing the Results
			
            System.out.println("Result of Square: "+java.util.Arrays.toString(dstArray0));
			int res = dstArray1[0];
			
			System.out.println("Result of Adding the Squares: "+ res);
			
			float finalRoot = finaResult[0];
			System.out.println("Norm of Vectors : "+ finalRoot);
        
		
		}catch(IOException exc)
		{
		exc.printStackTrace();
		}
		
    }
}