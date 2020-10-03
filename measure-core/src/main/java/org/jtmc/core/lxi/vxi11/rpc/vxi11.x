/* This file, vxi11.x, is the amalgamation of vxi11core.rpcl and vxi11intr.rpcl
 * which are part of the asynDriver (R4-5) EPICS module, which, at time of
 * writing, is available from:
 * http://www.aps.anl.gov/epics/modules/soft/asyn/index.html
 * More general information about EPICS is available from:
 * http://www.aps.anl.gov/epics/
 * This code is open source, and is covered under the copyright notice and
 * software license agreement shown below, and also at:
 * http://www.aps.anl.gov/epics/license/open.php
 * 
 * In order to comply with section 4.3 of the software license agreement, here
 * is a PROMINENT NOTICE OF CHNAGES TO THE SOFTWARE
 *      ===========================================
 * (1) This file, vxi11.x, is the concatenation of the files vxi11core.rpcl and
 *     vxi11intr.rpcl
 * (2) Tab spacing has been tidied up
 *
 * It is intended as a lightweight base for the vxi11 rpc protocol. If you
 * run rpcgen on this file, it will generate C files and headers, from which
 * it is relatively simple to write C programs to communicate with a range
 * of ethernet-enabled instruments, such as oscilloscopes and function 
 * generated by manufacturers such as Agilent and Tektronix (amongst many
 * others).
 * 
 * For what it's worth, this concatenation was done by Steve Sharples at
 * the University of Nottingham, UK, on 1 June 2006.
 *
 * Copyright notice and software license agreement follow, then the
 * original comments from vxi11core.rpcl etc.
 *
 ******************************************************************************
 * Copyright © 2006 <University of Chicago and other copyright holders>. All
 * rights reserved.
 ******************************************************************************
 * 
 ******************************************************************************
 * vxi11.x is distributed subject to the following license conditions:
 * SOFTWARE LICENSE AGREEMENT
 * Software: vxi11.x
 * 
 * 1. The "Software", below, refers to vxi11.x (in either source code, or
 *    binary form and accompanying documentation). Each licensee is addressed
 *    as "you" or "Licensee."
 *
 * 2. The copyright holders shown above and their third-party licensors hereby
 *    grant Licensee a royalty-free nonexclusive license, subject to the
 *    limitations stated herein and U.S. Government license rights.
 *
 * 3. You may modify and make a copy or copies of the Software for use within
 *    your organization, if you meet the following conditions:
 *       1. Copies in source code must include the copyright notice and this
 *          Software License Agreement.
 *       2. Copies in binary form must include the copyright notice and this
 *           Software License Agreement in the documentation and/or other
 *           materials provided with the copy.
 *
 * 4. You may modify a copy or copies of the Software or any portion of it,
 *    thus forming a work based on the Software, and distribute copies of such
 *     work outside your organization, if you meet all of the following
 *     conditions:
 *       1. Copies in source code must include the copyright notice and this
 *          Software License Agreement;
 *       2. Copies in binary form must include the copyright notice and this
 *          Software License Agreement in the documentation and/or other
 *          materials provided with the copy;
 *       3. Modified copies and works based on the Software must carry
 *          prominent notices stating that you changed specified portions of
 *          the Software.
 *
 * 5. Portions of the Software resulted from work developed under a U.S.
 *    Government contract and are subject to the following license: the
 *    Government is granted for itself and others acting on its behalf a
 *    paid-up, nonexclusive, irrevocable worldwide license in this computer
 *    software to reproduce, prepare derivative works, and perform publicly
 *    and display publicly.
 * 
 * 6. WARRANTY DISCLAIMER. THE SOFTWARE IS SUPPLIED "AS IS" WITHOUT WARRANTY OF
 *    ANY KIND. THE COPYRIGHT HOLDERS, THEIR THIRD PARTY LICENSORS, THE UNITED
 *    STATES, THE UNITED STATES DEPARTMENT OF ENERGY, AND THEIR EMPLOYEES: (1) 
 *    DISCLAIM ANY WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 *    ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE, TITLE OR NON-INFRINGEMENT, (2) DO NOT ASSUME ANY LEGAL LIABILITY
 *    OR RESPONSIBILITY FOR THE ACCURACY, COMPLETENESS, OR USEFULNESS OF THE
 *    SOFTWARE, (3) DO NOT REPRESENT THAT USE OF THE SOFTWARE WOULD NOT INFRINGE
 *    PRIVATELY OWNED RIGHTS, (4) DO NOT WARRANT THAT THE SOFTWARE WILL FUNCTION
 *    UNINTERRUPTED, THAT IT IS ERROR-FREE OR THAT ANY ERRORS WILL BE CORRECTED.
 *
 * 7. LIMITATION OF LIABILITY. IN NO EVENT WILL THE COPYRIGHT HOLDERS, THEIR
 *    THIRD PARTY LICENSORS, THE UNITED STATES, THE UNITED STATES DEPARTMENT OF
 *    ENERGY, OR THEIR EMPLOYEES: BE LIABLE FOR ANY INDIRECT, INCIDENTAL,
 *    CONSEQUENTIAL, SPECIAL OR PUNITIVE DAMAGES OF ANY KIND OR NATURE,
 *    INCLUDING BUT NOT LIMITED TO LOSS OF PROFITS OR LOSS OF DATA, FOR ANY
 *    REASON WHATSOEVER, WHETHER SUCH LIABILITY IS ASSERTED ON THE BASIS OF
 *    CONTRACT, TORT (INCLUDING NEGLIGENCE OR STRICT LIABILITY), OR OTHERWISE,
 *    EVEN IF ANY OF SAID PARTIES HAS BEEN WARNED OF THE POSSIBILITY OF SUCH
 *    LOSS OR DAMAGES.
 ******************************************************************************
 */

/******************************************************************************
 *
 * vxi11core.rpcl
 *
 *	This file is best viewed with a tabwidth of 4
 *
 ******************************************************************************
 *
 * TODO:
 *
 ******************************************************************************
 *
 *	Original Author:	someone from VXIbus Consortium
 *	Current Author:		Benjamin Franksen
 *	Date:			03-06-97
 *
 *	RPCL description of the core- and abort-channel of the TCP/IP Instrument 
 *	Protocol Specification.
 *
 *
 * Modification Log:
 * -----------------
 * .00	03-06-97	bfr		created this file
 *
 ******************************************************************************
 *
 * Notes: 
 *
 *	This stuff is literally from
 *
 *		VXI-11, Ref 1.0 : TCP/IP Instrument Protocol Specification
 *
 */

typedef long Device_Link;

enum Device_AddrFamily
{
	DEVICE_TCP,
	DEVICE_UDP
};

typedef long Device_Flags;

typedef long Device_ErrorCode;

struct Device_Error
{
	Device_ErrorCode error;
};

struct Create_LinkParms
{
	long			clientId;		/* implementation specific value */
	bool			lockDevice;		/* attempt to lock the device */
	unsigned long		lock_timeout;		/* time to wait for lock */
	string			device<>;		/* name of device */
};
struct Create_LinkResp
{
	Device_ErrorCode	error;
	Device_Link		lid;
	unsigned short		abortPort;		/* for the abort RPC */
	unsigned long		maxRecvSize;		/* max # of bytes accepted on write */
};
struct Device_WriteParms
{
	Device_Link		lid;			/* link id from create_link */
	unsigned long		io_timeout;		/* time to wait for I/O */
	unsigned long		lock_timeout;		/* time to wait for lock */
	Device_Flags		flags;			/* flags with options */
	opaque			data<>;			/* the data length and the data itself */
};
struct Device_WriteResp
{
	Device_ErrorCode	error;
	unsigned long		size;			/* # of bytes written */
};
struct Device_ReadParms
{
	Device_Link		lid;			/* link id from create_link */
	unsigned long		requestSize;		/* # of bytes requested */
	unsigned long		io_timeout;		/* time to wait for I/O */
	unsigned long		lock_timeout;		/* time to wait for lock */
	Device_Flags		flags;			/* flags with options */
	char			termChar;		/* valid if flags & termchrset */
};
struct Device_ReadResp
{
	Device_ErrorCode	error;
	long			reason;			/* why read completed */
	opaque			data<>;			/* the data length and the data itself */
};
struct Device_ReadStbResp
{
	Device_ErrorCode	error;
	unsigned char		stb;			/* the returned status byte */
};
struct Device_GenericParms
{
	Device_Link		lid;			/* link id from create_link */
	Device_Flags		flags;			/* flags with options */
	unsigned long		lock_timeout;		/* time to wait for lock */
	unsigned long		io_timeout;		/* time to wait for I/O */
};
struct Device_RemoteFunc
{
	unsigned long		hostAddr;		/* host servicing interrupt */
	unsigned long		hostPort;		/* valid port # on client */
	unsigned long		progNum;		/* DEVICE_INTR */
	unsigned long		progVers;		/* DEVICE_INTR_VERSION */
	Device_AddrFamily	progFamily;		/* DEVICE_UDP | DEVICE_TCP */
};
struct Device_EnableSrqParms
{
	Device_Link		lid;			/* link id from create_link */
	bool			enable;			/* enable or disable intr's */
	opaque			handle<40>;		/* host specific data */
};
struct Device_LockParms
{
	Device_Link		lid;			/* link id from create_link */
	Device_Flags		flags;			/* contains the waitlock flag */
	unsigned long		lock_timeout;		/* time to wait for lock */
};
struct Device_DocmdParms
{
	Device_Link		lid;			/* link id from create_link */
	Device_Flags		flags;			/* flags with options */
	unsigned long		io_timeout;		/* time to wait for I/O */
	unsigned long		lock_timeout;		/* time to wait for lock */
	long			cmd;			/* which command to execute */
	bool			network_order;		/* client's byte order */
	long			datasize;		/* size of individual data elements */
	opaque			data_in<>;		/* docmd data parameters */
};
struct Device_DocmdResp
{
	Device_ErrorCode	error;
	opaque			data_out<>;		/* returned data parameters */
};

struct Device_SrqParms {
	opaque	handle<>;
};

program DEVICE_ASYNC
{
	version DEVICE_ASYNC_VERSION
	{
		Device_Error		device_abort		(Device_Link)		= 1;
	} = 1;
} = 0x0607B0;

program DEVICE_CORE
{
	version DEVICE_CORE_VERSION
	{
		Create_LinkResp		create_link		(Create_LinkParms)	= 10;
		Device_WriteResp	device_write		(Device_WriteParms)	= 11;
		Device_ReadResp		device_read		(Device_ReadParms)	= 12;
		Device_ReadStbResp	device_readstb		(Device_GenericParms)	= 13;
		Device_Error		device_trigger		(Device_GenericParms)	= 14;
		Device_Error		device_clear		(Device_GenericParms)	= 15;
		Device_Error		device_remote		(Device_GenericParms)	= 16;
		Device_Error		device_local		(Device_GenericParms)	= 17;
		Device_Error		device_lock		(Device_LockParms)	= 18;
		Device_Error		device_unlock		(Device_Link)		= 19;
		Device_Error		device_enable_srq	(Device_EnableSrqParms)	= 20;
		Device_DocmdResp	device_docmd		(Device_DocmdParms)	= 22;
		Device_Error		destroy_link		(Device_Link)		= 23;
		Device_Error		create_intr_chan	(Device_RemoteFunc)	= 25;
		Device_Error		destroy_intr_chan	(void)			= 26;
	} = 1;
} = 0x0607AF;

/******************************************************************************
 *
 * vxi11intr.rpcl
 *
 *	This file is best viewed with a tabwidth of 4
 *
 ******************************************************************************
 *
 * TODO:
 *
 ******************************************************************************
 *
 *	Original Author:	someone from VXIbus Consortium
 *	Current Author:		Benjamin Franksen
 *	Date:				03-06-97
 *
 *	RPCL description of the intr-channel of the TCP/IP Instrument Protocol 
 *	Specification.
 *
 *
 * Modification Log:
 * -----------------
 * .00	03-06-97	bfr		created this file
 *
 ******************************************************************************
 *
 * Notes: 
 *
 *	This stuff is literally from
 *
 *		"VXI-11, Ref 1.0 : TCP/IP Instrument Protocol Specification"
 *
 */
 
 


program DEVICE_INTR {
	version DEVICE_INTR_VERSION {
		void			device_intr_srq		(Device_SrqParms)	= 30;
	} = 1;
} = 0x0607B1;
