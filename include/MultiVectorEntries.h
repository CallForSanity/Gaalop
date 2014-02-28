#ifndef MULTIVECTORENTRIES_H
#define MULTIVECTORENTRIES_H

enum MVEntries
{
	// scalar, 0
	SCALAR = 0,
	// vector, 1 - 5
	E1, E2, E3, EINF, E0,
	// bivector, 6 - 15
	E1E2, E1E3, E1EINF, E1E0,
	E2E3, E2EINF, E2E0,
	E3EINF, E3E0,
	EINFE0, // e_inf ^ e0 is often abbreviated 'E'
	// trivector, 16 - 25
	E1E2E3, E1E2EINF, E1E2E0,
	E1E3EINF, E1E3E0, E1EINFE0,
	E2E3EINF, E2E3E0, E2EINFE0,
	E3EINFE0,
	// quadvector, 26 - 30
	E1E2E3EINF,
	E1E2E3E0,
	E1E2EINFE0,
	E1E3EINFE0,
	E2E3EINFE0,
	// pseudoscalar, 31
	PSEUDOSCALAR = 31,
	E1E2E3E4EINFE0 = 31
};

#endif // MULTIVECTORENTRIES_H