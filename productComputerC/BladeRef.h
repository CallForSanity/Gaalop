/*
 * BladeRef.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef BLADEREF_H_
#define BLADEREF_H_

class BladeRef {
private:
	signed char prefactor;
	int index;
public:
	BladeRef(signed char prefactor, int index);
	virtual ~BladeRef();

	inline int getIndex() const
    {
        return index;
    }

	inline signed char getPrefactor() const
    {
        return prefactor;
    }

	inline void setIndex(int index)
    {
        this->index = index;
    }

	inline void setPrefactor(signed char prefactor)
    {
        this->prefactor = prefactor;
    }
};

#endif /* BLADEREF_H_ */
