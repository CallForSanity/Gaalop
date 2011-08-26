#include <assert.h>
#include <vector>
#include "cl.hpp"

#ifdef _MSC_VER
#undef min;
#undef max;
#endif

template<typename T> class clDeviceVector {
public:
	clDeviceVector() {
	}
	clDeviceVector(cl::Context context, cl::CommandQueue commandQueue,
			const size_t size = 0, const cl_int usage = CL_MEM_READ_WRITE) {
		init(context, commandQueue, size, usage);
	}
	~clDeviceVector() {
	}

	void init(cl::Context context_, cl::CommandQueue commandQueue_,
			const size_t size_ = 0, const cl_int usage_ = CL_MEM_READ_WRITE) {
		context = context_;
		commandQueue = commandQueue_;
		usage = usage_;

		resize(size_);
	}

	clDeviceVector<T>& operator =(const std::vector<T>& in) {
		if (in.size() > numElements)
			resize(in.size());

		commandQueue.enqueueWriteBuffer(buffer, CL_TRUE, 0,
										std::min(numElements,in.size()) * sizeof(T),
										&in.front() );
	}

	clDeviceVector<T>& operator =(const T* in) {
		const size_t numElementsIn = sizeof(in) / sizeof(T);
		if (numElementsIn > numElements)
			resize(numElementsIn);

		commandQueue.enqueueWriteBuffer(buffer, CL_TRUE, 0,
										std::min(numElements,numElementsIn) * sizeof(T),
										in );
	}

	std::vector<T>& copyTo(std::vector<T>& out) {
		commandQueue.enqueueReadBuffer(buffer, CL_TRUE, 0,
										std::min(numElements,out.size()) * sizeof(T),
										&out.front() );
	}

	T* copyTo(T* out) {
		const size_t numElementsOut = sizeof(out) / sizeof(T);
		commandQueue.enqueueReadBuffer(buffer, CL_TRUE, 0,
										std::min(numElements,numElementsOut) * sizeof(T),
										out );
	}

	void resize(const size_t size_) {
		if((numElements = size_) != 0)
			buffer = cl::Buffer(context, usage,
								size_ * sizeof(T)); // TODO not copying old data?
	}

	inline const size_t size() const {return numElements;}
	inline cl::Buffer& getBuffer() {
		return buffer;
	}
private:
	// attributes
	size_t numElements;
	cl_int usage;
	cl::Context context;
	cl::CommandQueue commandQueue;
	cl::Buffer buffer;
};

/*template<typename T> std::vector<T>& operator = (std::vector<T>& out,const clDeviceVector<T>& in)
 {
 clEnqueueReadBuffer(cqCommandQueue, in.getMemObject(), CL_FALSE, 0, in.size_() * size_of(T), &out.front(), 0, NULL, NULL);
 return out;
 }*/
